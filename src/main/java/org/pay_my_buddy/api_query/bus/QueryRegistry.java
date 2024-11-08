package org.pay_my_buddy.api_query.bus;

import org.pay_my_buddy.api_query.QueryHandler;
import org.pay_my_buddy.api_query.Query;

public interface QueryRegistry {

    <QUERY extends Query<RESPONSE>, RESPONSE> QueryHandler<QUERY, RESPONSE> get(Class<QUERY> queryClass);
}
