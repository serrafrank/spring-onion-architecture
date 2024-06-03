package org.pay_my_buddy.presentation.handler_exception;

import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.entity.exception.generic.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * This class represents an API exception handler.
 * It uses the @RestControllerAdvice annotation to provide global exception handling.
 * It logs the requested URL and the exception message, and returns an ErrorResponse.
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * This method handles all exceptions.
     * It logs the requested URL and the exception message, and returns an ErrorResponse.
     *
     * @param request   the WebRequest that caused the exception
     * @param exception the Exception that was thrown
     * @return a ResponseEntity containing an ErrorResponse
     */

    @ExceptionHandler({IllegalRequestException.class, IllegalArgumentException.class})
    public ResponseEntity<Object> handleBadRequestException(WebRequest request, Exception exception) {
        return handle(exception, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(WebRequest request, ConflictException exception) {
        return handle(exception, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbiddenException(WebRequest request, ForbiddenException exception) {
        return handle(exception, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<Object> handleInternalErrorException(WebRequest request, InternalErrorException exception) {
        return handle(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(WebRequest request, ResourceNotFoundException exception) {
        return handle(exception, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(WebRequest request, UnauthorizedException exception) {
        return handle(exception, HttpStatus.UNAUTHORIZED, request);
    }

    private ResponseEntity<Object> handle(Exception exception, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(exception, null, new HttpHeaders(), status, request);
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        log.error("Requested URL : " + ((ServletWebRequest) request).getRequest().getRequestURI());
        log.error(exception.getMessage(), exception);
        body = new ErrorResponse(request, exception, statusCode);

        return super.handleExceptionInternal(exception, body, headers, statusCode, request);
    }
}