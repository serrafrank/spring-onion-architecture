package org.pay_my_buddy.shared.domain.exception;

/**
 * This class represents an InternalErrorException.
 * It extends the RuntimeException class
 */
public class InternalErrorException extends GenericApiRequestException {

    /**
     * Default constructor for InternalErrorException.
     * It sets a default message for this exception.
     */
    public InternalErrorException() {
        this("Internal Error");
    }

    /**
     * Constructor for InternalErrorException with a custom message.
     *
     * @param message The custom message for this exception.
     */
    public InternalErrorException(String message) {
        super(message);
    }

    /**
     * Constructor for InternalErrorException with a custom message and a cause.
     *
     * @param message The custom message for this exception.
     * @param cause   The cause of this exception.
     */
    public InternalErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for InternalErrorException with a custom message and arguments for formatting the message.
     *
     * @param message The custom message for this exception.
     * @param args    The arguments for formatting the message.
     */
    public InternalErrorException(String message, Object... args) {
        super(message, args);
    }
}