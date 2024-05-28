package org.pay_my_buddy.entity.common.api;

import org.pay_my_buddy.entity.common.exception.generic.InternalErrorException;

/**
 * This exception is thrown when no use case is found for a query or a command.
 */
public class NoHandlerFoundException extends InternalErrorException {
    public NoHandlerFoundException(Class<?> request) {
        super("No suitable handler found for: " + request.getCanonicalName());
    }
}
