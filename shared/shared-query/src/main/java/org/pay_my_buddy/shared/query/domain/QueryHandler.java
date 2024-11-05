package org.pay_my_buddy.shared.query.domain;

import org.pay_my_buddy.shared.common.domain.api.query.Query;

@FunctionalInterface
public interface QueryHandler<QUERY extends Query<RESPONSE>, RESPONSE> {
    RESPONSE handle(QUERY query);
}
