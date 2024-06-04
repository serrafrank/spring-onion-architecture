package org.pay_my_buddy.entity.exception.generic;


/**
 * This class represents a IllegalRequestException.
 */
public class IllegalRequestException extends GenericApiRequestException {
    /**
     * Default constructor for IllegalRequestException.
     * It sets a default message for this exception.
     */
    public IllegalRequestException() {
        this("Bad Request");
    }

    /**
     * Constructor for IllegalRequestException with a custom message.
     *
     * @param message The custom message for this exception.
     */
    public IllegalRequestException(String message) {
        super(message);
    }

    /**
     * Constructor for IllegalRequestException with a custom message and a cause.
     *
     * @param message The custom message for this exception.
     * @param cause   The cause of this exception.
     */
    public IllegalRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for IllegalRequestException with a custom message and arguments for formatting the message.
     *
     * @param message The custom message for this exception.
     * @param args    The arguments for formatting the message.
     */
    public IllegalRequestException(String message, Object... args) {
        super(message, args);
    }
}