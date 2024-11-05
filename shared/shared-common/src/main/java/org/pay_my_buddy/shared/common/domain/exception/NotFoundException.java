package org.pay_my_buddy.shared.common.domain.exception;

/**
 * This class represents an NotFoundException.
 * It extends the RuntimeException class and sets the HTTP status to NOTFOUND.
 */
public class NotFoundException extends GenericApiRequestException {
    /**
     * Default constructor for NotFoundException.
     * It sets a default message for this exception.
     */
    public NotFoundException() {
        this("NotFound");
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
        super(message, args);
    }
}
