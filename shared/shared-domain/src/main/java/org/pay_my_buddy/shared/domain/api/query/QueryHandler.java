package org.pay_my_buddy.shared.domain.api.query;

@FunctionalInterface
public interface QueryHandler<QUERY extends Query<RESPONSE>, RESPONSE> {
    RESPONSE handle(QUERY query);
}
