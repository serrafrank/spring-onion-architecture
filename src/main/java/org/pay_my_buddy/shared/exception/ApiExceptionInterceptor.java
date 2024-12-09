package org.pay_my_buddy.shared.exception;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * This class represents an API exception handler.
 * It uses the @RestControllerAdvice annotation to provide global exception handling.
 * It logs the requested URL and the exception message, and returns an ErrorResponse.
 */
@Slf4j
@ControllerAdvice
public class ApiExceptionInterceptor extends ResponseEntityExceptionHandler {

    private static final String CURRENT_PACKAGE = ApiExceptionInterceptor.class.getPackageName();
    private static final String ROOT_PACKAGE = CURRENT_PACKAGE.split("\\.")[0] + "." + CURRENT_PACKAGE.split("\\.")[1];


    private static List<StackTraceElement> filterStackTrace(StackTraceElement[] stackTrace, String packageName) {
        return Arrays.stream(stackTrace)
                .filter(element -> element.getClassName().startsWith(packageName))
                .toList();
    }

    private static List<StackTraceElement> filterStackTrace(Exception exception, String packageName) {
        int maxLength = 7;

        List<StackTraceElement> filteredStackTrace = filterStackTrace(exception.getStackTrace(), packageName);
        List<StackTraceElement> limitedStackTrace;
        if (filteredStackTrace.size() > maxLength) {
            limitedStackTrace = filteredStackTrace.subList(0, maxLength);
        } else {
            limitedStackTrace = filteredStackTrace;
        }

        return limitedStackTrace;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest request) {
        return createResponseEntity(exception, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<Object> handleSystemException(SystemException exception, WebRequest request) {
        Throwable cause = exception.getCause();

        if (cause == null) {
            return createResponseEntity(exception, request, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return createResponseEntity((Exception) cause, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException exception, WebRequest request) {
        Throwable cause = exception.getCause();

        if (cause == null) {
            return createResponseEntity(exception, request, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cause instanceof GenericApiRequestException genericApiRequestException) {
            return handleGenericException(genericApiRequestException, request);
        }

        return createResponseEntity((Exception) cause, request, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private ResponseEntity<Object> handleGenericException(GenericApiRequestException exception, WebRequest request) {
        HttpStatus status = switch (exception) {
            case BadArgumentException ignored -> HttpStatus.BAD_REQUEST;
            case ConflictException ignored -> HttpStatus.CONFLICT;
            case ForbiddenException ignored -> HttpStatus.FORBIDDEN;
            case NotFoundException ignored -> HttpStatus.NOT_FOUND;
            case UnauthorizedException ignored -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        return createResponseEntity(exception, request, status);
    }

    private ResponseEntity<Object> createResponseEntity(Exception exception, WebRequest request, HttpStatus status) {
        ProblemDetail body = createProblemDetail(exception, HttpStatusCode.valueOf(status.value()), exception.getMessage(), null, null, request);
        return this.handleExceptionInternal(exception, body, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String[] args = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(msg -> "Field '" + ((DefaultMessageSourceResolvable) Objects.requireNonNull(msg.getArguments())[0]).getDefaultMessage() + "' : " + msg.getDefaultMessage())
                .toArray(String[]::new);


        String defaultDetail = "Argument not valid";
        ProblemDetail body = this.createProblemDetail(exception, status, defaultDetail, null, args, request);
        return this.handleExceptionInternal(exception, body, headers, status, request);
    }

    @Nullable
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errorList = exception.getAllValidationResults()
                .stream()
                .map(ParameterValidationResult::getResolvableErrors)
                .flatMap(Collection::stream)
                .map(MessageSourceResolvable::getDefaultMessage)
                .toArray(String[]::new);

        String defaultDetail = "Argument not valid";

        ProblemDetail body = this.createProblemDetail(exception, status, defaultDetail, null, errorList, request);
        return this.handleExceptionInternal(exception, body, headers, status, request);
    }

    @Override
    protected ProblemDetail createProblemDetail(Exception exception, HttpStatusCode status, String defaultDetail, @Nullable String detailMessageCode, @Nullable Object[] detailMessageArguments, WebRequest request) {

        String message = exception.getClass().getSimpleName() + ": " + exception.getMessage();

        if (status.is5xxServerError()) {
            log.error(message, exception);
        } else {


            StringBuilder sb = new StringBuilder();
            sb.append(message).append("\n");
            filterStackTrace(exception, "org.pay_my_buddy").forEach(element -> sb.append("\tat ").append(element).append("\n"));
            sb.append("\t...");


            log.warn(sb.toString());
        }

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, defaultDetail);
        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        if (detailMessageCode != null) {
            problemDetail.setProperty("messageCode", detailMessageCode);
        }
        if (detailMessageArguments != null) {
            problemDetail.setProperty("details", detailMessageArguments);
        }

        return problemDetail;
    }

    private String concat(Object[] args) {
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            sb.append(arg.toString()).append("\n");
        }
        return sb.toString();
    }
}
