package org.pay_my_buddy.entity.exception;

/**
 * This class represents a NotFoundException.
 * It extends the RuntimeException class and sets the HTTP status to NOT_FOUND.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Default constructor for NotFoundException.
     * Sets the HTTP status to NOT_FOUND.
     */
    public NotFoundException() {
        this("Not found");
    }

    /**
     * Constructor for NotFoundException with a custom message.
     *
     * @param message The custom message for this exception.
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor for NotFoundException with a custom message and a cause.
     *
     * @param message The custom message for this exception.
     * @param cause   The cause of this exception.
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for NotFoundException with a custom message and arguments for formatting the message.
     *
     * @param message The custom message for this exception.
     * @param args    The arguments for formatting the message.
     */
    public NotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }
}