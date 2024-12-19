package org.pay_my_buddy.core.query.application;


import org.pay_my_buddy.core.framework.domain.UseCaseHandler;
import org.pay_my_buddy.core.framework.domain.message.Query;

@FunctionalInterface
public interface QueryHandler<QUERY extends Query, RESPONSE> extends UseCaseHandler<QUERY, RESPONSE> {
    RESPONSE handle(QUERY query);
}
