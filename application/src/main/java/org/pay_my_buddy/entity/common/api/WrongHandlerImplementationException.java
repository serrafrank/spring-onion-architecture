package org.pay_my_buddy.entity.common.api;

import org.pay_my_buddy.entity.common.api.query.QueryHandler;
import org.pay_my_buddy.entity.exception.generic.InternalErrorException;

public class WrongHandlerImplementationException extends InternalErrorException {
    public WrongHandlerImplementationException(QueryHandler<?, ?> handler) {
        super(handler.getClass().getCanonicalName() + " has incorrect implementation");
    }
}
