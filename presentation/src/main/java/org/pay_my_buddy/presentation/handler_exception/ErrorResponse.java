package org.pay_my_buddy.presentation.handler_exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represents an ErrorResponse.
 * It contains information about an exception that occurred during the processing of an HTTP request.
 * The information includes the timestamp, status, error, exception, message, fieldError, and path.
 */
@JsonPropertyOrder({"timestamp", "status", "error", "exception", "message", "fieldError", "path"})
public class ErrorResponse {

    private final Exception exception;

    /**
     * -- GETTER --
     * Returns the request URL.
     *
     * @return The request URL.
     */
    @Getter
    private final String path;

    private HttpStatusCode status;

    /**
     * Constructor for ErrorResponse.
     *
     * @param request   The HttpServletRequest that caused the exception.
     * @param exception The exception that was thrown.
     */
    public ErrorResponse(HttpServletRequest request, Exception exception) {
        this.exception = exception;
        this.path = request.getRequestURI();
    }

    /**
     * Constructor for ErrorResponse.
     *
     * @param request   The WebRequest that caused the exception.
     * @param exception The exception that was thrown.
     */
    public ErrorResponse(WebRequest request, Exception exception) {
        this.exception = exception;
        this.path = request.getContextPath();
    }


    /**
     * Constructor for ErrorResponse.
     *
     * @param request   The WebRequest that caused the exception.
     * @param exception The exception that was thrown.
     * @param status    The HTTP status.
     */
    public ErrorResponse(WebRequest request, Exception exception, HttpStatusCode status) {
        this.exception = exception;
        this.path = request.getContextPath();
        this.status = status;
    }


    /**
     * Returns the current time.
     *
     * @return The current date and time.
     */
    public OffsetDateTime getTimestamp() {
        return OffsetDateTime.now();
    }

    /**
     * Returns the status of the response.
     *
     * @return The status code of the response.
     */
    public int getStatus() {
        return this.getHttpStatus().value();
    }

    /**
     * Returns the error message associated with a given HTTP status code.
     *
     * @return The status code and the reason phrase.
     */
    public String getError() {
        return this.getHttpStatus().getReasonPhrase();
    }

    /**
     * Returns the exception that was thrown.
     *
     * @return The name of the exception class.
     */
    public String getException() {
        return this.exception.getClass().getSimpleName();
    }

    /**
     * Returns the message of the exception.
     *
     * @return The message of the exception that was thrown.
     */
    public String getMessage() {
        return this.exception.getMessage();
    }

    /**
     * Returns a map of field errors.
     * If the exception is an instance of MethodArgumentNotValidException, it collects all the FieldErrors from the BindingResult and converts them into a Map.
     *
     * @return A map of field names to error messages.
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public Map<String, String> getFieldError() {
        if (this.exception instanceof MethodArgumentNotValidException) {
            return ((MethodArgumentNotValidException) this.exception).getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            DefaultMessageSourceResolvable::getDefaultMessage
                    ));
        }
        return Collections.emptyMap();
    }

    /**
     * Returns the HTTP status.
     * If the exception is an instance of MethodArgumentNotValidException, it returns BAD_REQUEST.
     * If the exception is an instance of GenericApiRequestException, it returns the status of the exception.
     * Otherwise, it returns INTERNAL_SERVER_ERROR.
     *
     * @return The HTTP status.
     */
    private HttpStatus getHttpStatus() {
        if (this.status != null) {
            return HttpStatus.valueOf(this.status.value());
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}