package org.pay_my_buddy.entity.exception;


/**
 * This class represents a BadRequestException.
 */
public class BadRequestException extends RuntimeException {
    /**
     * Default constructor for BadRequestException.
     * It sets a default message for this exception.
     */
    public BadRequestException() {
        this("Bad Request");
    }

    /**
     * Constructor for BadRequestException with a custom message.
     *
     * @param message The custom message for this exception.
     */
    public BadRequestException(String message) {
        super(message);
    }

    /**
     * Constructor for BadRequestException with a custom message and a cause.
     *
     * @param message The custom message for this exception.
     * @param cause   The cause of this exception.
     */
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for BadRequestException with a custom message and arguments for formatting the message.
     *
     * @param message The custom message for this exception.
     * @param args    The arguments for formatting the message.
     */
    public BadRequestException(String message, Object... args) {
        super(String.format(message, args));
    }
}