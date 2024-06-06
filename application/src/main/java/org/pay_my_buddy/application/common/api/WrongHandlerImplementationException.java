package org.pay_my_buddy.application.common.api;

import org.pay_my_buddy.entity.exception.InternalErrorException;

public class WrongHandlerImplementationException extends InternalErrorException {
    public WrongHandlerImplementationException(QueryHandler<?, ?> handler) {
        super(handler.getClass().getCanonicalName() + " has incorrect implementation");
    }
}
