package org.pay_my_buddy.api_query.bus;

import org.pay_my_buddy.api_query.Query;
import org.pay_my_buddy.api_query.QueryHandler;

public interface QueryRegistry {

	<QUERY extends Query<RESPONSE>, RESPONSE> QueryHandler<QUERY, RESPONSE> get(Class<QUERY> queryClass);
}
