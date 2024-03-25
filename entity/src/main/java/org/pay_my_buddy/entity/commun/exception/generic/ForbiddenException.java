package org.pay_my_buddy.entity.commun.exception.generic;

/**
 * This class represents a ForbiddenException.
 * It extends the RuntimeException class and sets the HTTP status to FORBIDDEN.
 */
public class ForbiddenException extends RuntimeException {

    /**
     * Default constructor for ForbiddenException.
     * It sets a default message for this exception.
     */
    public ForbiddenException() {
        this("Forbidden");
    }

    /**
     * Constructor for ForbiddenException with a custom message.
     *
     * @param message The custom message for this exception.
     */
    public ForbiddenException(String message) {
        super(message);
    }

    /**
     * Constructor for ForbiddenException with a custom message and a cause.
     *
     * @param message The custom message for this exception.
     * @param cause   The cause of this exception.
     */
    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for ForbiddenException with a custom message and arguments for formatting the message.
     *
     * @param message The custom message for this exception.
     * @param args    The arguments for formatting the message.
     */
    public ForbiddenException(String message, Object... args) {
        super(String.format(message, args));
    }
}