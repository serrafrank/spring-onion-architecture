package org.pay_my_buddy.shared.query.domain;

import org.pay_my_buddy.shared.common.domain.api.query.Query;

public interface QueryRegistry {

    <QUERY extends Query<RESPONSE>, RESPONSE> QueryHandler<QUERY, RESPONSE> get(Class<QUERY> queryClass);
}
