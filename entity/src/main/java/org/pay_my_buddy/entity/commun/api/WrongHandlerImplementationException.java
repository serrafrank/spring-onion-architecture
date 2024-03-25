package org.pay_my_buddy.entity.commun.api;

import org.pay_my_buddy.entity.commun.api.query.QueryHandler;
import org.pay_my_buddy.entity.commun.exception.generic.InternalErrorException;

public class WrongHandlerImplementationException extends InternalErrorException {
    public WrongHandlerImplementationException(QueryHandler<?, ?> handler) {
        super(handler.getClass().getCanonicalName() + " has incorrect implementation");
    }
}
