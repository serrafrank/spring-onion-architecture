package org.pay_my_buddy.shared.domain.exception;

/**
 * This class represents an UnauthorizedException.
 * It extends the RuntimeException class and sets the HTTP status to UNAUTHORIZED.
 */
public class UnauthorizedException extends GenericApiRequestException {
    /**
     * Default constructor for UnauthorizedException.
     * It sets a default message for this exception.
     */
    public UnauthorizedException() {
        this("Unauthorized");
    }

    /**
     * Constructor for UnauthorizedException with a custom message.
     *
     * @param message The custom message for this exception.
     */
    public UnauthorizedException(String message) {
        super(message);
    }

    /**
     * Constructor for UnauthorizedException with a custom message and a cause.
     *
     * @param message The custom message for this exception.
     * @param cause   The cause of this exception.
     */
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for UnauthorizedException with a custom message and arguments for formatting the message.
     *
     * @param message The custom message for this exception.
     * @param args    The arguments for formatting the message.
     */
    public UnauthorizedException(String message, Object... args) {
        super(message, args);
    }
}