package org.pay_my_buddy.entity.common.exception.generic;

/**
 * This class represents a ConflictException.
 * It extends the RuntimeException class.
 */
public class ConflictException extends RuntimeException {

    /**
     * Default constructor for ConflictException.
     * It sets a default message for this exception.
     */
    public ConflictException() {
        this("Conflict");
    }

    /**
     * Constructor for ConflictException with a custom message.
     *
     * @param message The custom message for this exception.
     */
    public ConflictException(String message) {
        super(message);
    }


    /**
     * Constructor for ConflictException with a custom message and a cause.
     *
     * @param message The custom message for this exception.
     * @param cause   The cause of this exception.
     */
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for ConflictException with a custom message and arguments for formatting the message.
     *
     * @param message The custom message for this exception.
     * @param args    The arguments for formatting the message.
     */
    public ConflictException(String message, Object... args) {
        super(String.format(message, args));
    }
}