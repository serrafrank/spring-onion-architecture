package org.pay_my_buddy.entity.common.api;

import org.pay_my_buddy.entity.common.exception.generic.InternalErrorException;

import java.util.Set;

/**
 * This exception is thrown when no use case is found for a query or a command.
 */
public class DuplicateHandlerFoundException extends InternalErrorException {
    public DuplicateHandlerFoundException(Set<String> duplicateHandlers) {
        super("Duplicate handlers found for " + duplicateHandlers);
    }
}
