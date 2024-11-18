package org.pay_my_buddy.api_query;

@FunctionalInterface
public interface QueryHandler<QUERY extends Query<RESPONSE>, RESPONSE> {
    RESPONSE handle(QUERY query);
}
