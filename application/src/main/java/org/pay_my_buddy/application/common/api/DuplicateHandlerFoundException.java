package org.pay_my_buddy.application.common.api;

import org.pay_my_buddy.entity.exception.InternalErrorException;

/**
 * This exception is thrown when no use case is found for a query or a command.
 */
public class DuplicateHandlerFoundException extends InternalErrorException {
    public DuplicateHandlerFoundException(String duplicateHandlers) {
        super("Duplicate handlers found for " + duplicateHandlers);
    }
}
