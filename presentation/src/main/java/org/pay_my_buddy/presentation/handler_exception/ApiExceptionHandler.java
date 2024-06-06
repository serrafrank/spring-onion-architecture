package org.pay_my_buddy.presentation.handler_exception;

import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.entity.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;


/**
 * This class represents an API exception handler.
 * It uses the @RestControllerAdvice annotation to provide global exception handling.
 * It logs the requested URL and the exception message, and returns an ErrorResponse.
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * This method handles all exceptions.
     * It logs the requested URL and the exception message, and returns an ErrorResponse.
     *
     * @param request   the WebRequest that caused the exception
     * @param exception the Exception that was thrown
     * @return a ResponseEntity containing an ErrorResponse
     */

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleGenericException(WebRequest request, RuntimeException exception) {
        HttpStatus status = switch (exception) {
            case IllegalRequestException i -> HttpStatus.BAD_REQUEST;
            case ConflictException i -> HttpStatus.CONFLICT;
            case ForbiddenException i -> HttpStatus.FORBIDDEN;
            case ResourceNotFoundException i -> HttpStatus.NOT_FOUND;
            case UnauthorizedException i -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        return handleExceptionInternal(exception, status, request);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Object> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception, WebRequest request) {
        return handleExceptionInternal(exception, HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, HttpStatus status, WebRequest request) {
        final String requestUrl = ((ServletWebRequest) request).getRequest().getRequestURI();

        if (status.is5xxServerError()) {
            log.error("AN UNPROCESSED ERROR {} - {} OCCURRED on URL {} with message \"{}\"", status.value(), status.getReasonPhrase(), requestUrl, exception.getMessage(), exception);
        } else {
            log.error("An error {} - {} occurred on URL {} with message \"{}\"", status.value(), status.getReasonPhrase(), requestUrl, exception.getMessage(), exception);
        }

        ErrorResponse body = new ErrorResponse(request, exception, status);

        return new ResponseEntity<>(body, new HttpHeaders(), status);
    }
}