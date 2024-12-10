package org.pay_my_buddy.core.framework.domain.exception;


/**
 * This class represents a BadArgumentException.
 */
public class BadArgumentException extends GenericApiRequestException {
    /**
     * Default constructor for BadArgumentException.
     * It sets a default message for this exception.
     */
    public BadArgumentException() {
        this("Bad Request");
    }

    /**
     * Constructor for BadArgumentException with a custom message.
     *
     * @param message The custom message for this exception.
     */
    public BadArgumentException(String message) {
        super(message);
    }

    /**
     * Constructor for BadArgumentException with a custom message and a cause.
     *
     * @param message The custom message for this exception.
     * @param cause   The cause of this exception.
     */
    public BadArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for BadArgumentException with a custom message and arguments for formatting the message.
     *
     * @param message The custom message for this exception.
     * @param args    The arguments for formatting the message.
     */
    public BadArgumentException(String message, Object... args) {
        super(message, args);
    }
}
