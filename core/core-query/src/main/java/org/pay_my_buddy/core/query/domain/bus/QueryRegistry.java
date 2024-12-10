package org.pay_my_buddy.core.query.domain.bus;

import org.pay_my_buddy.core.framework.domain.message.Query;
import org.pay_my_buddy.core.query.application.QueryHandler;

public interface QueryRegistry {

    <QUERY extends Query<RESPONSE>, RESPONSE> QueryHandler<QUERY, RESPONSE> get(Class<QUERY> queryClass);
}
