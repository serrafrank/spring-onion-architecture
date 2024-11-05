package org.pay_my_buddy.shared.domain.api.query;

public interface QueryRegistry {

    <QUERY extends Query<RESPONSE>, RESPONSE> QueryHandler<QUERY, RESPONSE> get(Class<QUERY> queryClass);
}
