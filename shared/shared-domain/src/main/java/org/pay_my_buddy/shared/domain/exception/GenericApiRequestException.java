package org.pay_my_buddy.shared.domain.exception;


/**
 * This class represents a generic API request exception.
 * It extends the RuntimeException class and includes an HTTP status.
 */
public class GenericApiRequestException extends RuntimeException {


    public GenericApiRequestException() {
        super();
    }

    /**
     * Constructor for GenericApiRequestException with a custom message and a status.
     *
     * @param message The custom message for this exception.
     */
    public GenericApiRequestException(String message) {
        super(message);
    }

    /**
     * Constructor for GenericApiRequestException with a custom message, a cause and a status.
     *
     * @param message The custom message for this exception.
     * @param cause   The cause of this exception.
     */
    public GenericApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for GenericApiRequestException with a custom message and arguments for formatting the message.
     *
     * @param message The custom message for this exception.
     * @param args    The arguments for formatting the message.
     */
    public GenericApiRequestException(String message, Object[] args) {
        super(String.format(message, args));
    }
}