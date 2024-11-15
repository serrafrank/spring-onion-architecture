package org.pay_my_buddy.shared.exception;

import org.pay_my_buddy.shared.EntityId;

/**
 * This class represents a NotFoundException.
 * It extends the RuntimeException class and sets the HTTP status to NOT_FOUND.
 */
public class ResourceNotFoundException extends GenericApiRequestException {

    /**
     * Default constructor for NotFoundException.
     * Sets the HTTP status to NOT_FOUND.
     */
    public ResourceNotFoundException() {
        this("Resource Not found");
    }

    public ResourceNotFoundException(EntityId id) {
        this("Resource with userId %s not found", id);
    }

    /**
     * Constructor for NotFoundException with a custom message.
     *
     * @param message The custom message for this exception.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor for NotFoundException with a custom message and a cause.
     *
     * @param message The custom message for this exception.
     * @param cause   The cause of this exception.
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for NotFoundException with a custom message and arguments for formatting the message.
     *
     * @param message The custom message for this exception.
     * @param args    The arguments for formatting the message.
     */
    public ResourceNotFoundException(String message, Object... args) {
        super(message, args);
    }
}
