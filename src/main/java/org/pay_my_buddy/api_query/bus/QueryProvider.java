package org.pay_my_buddy.api_query.bus;

import org.pay_my_buddy.api_query.QueryHandler;
import org.springframework.context.ApplicationContext;

@SuppressWarnings("unchecked")
class QueryProvider<QUERY_HANDLER extends QueryHandler<?, ?>> {

    private final ApplicationContext applicationContext;
    private final Class<QUERY_HANDLER> type;

    QueryProvider(ApplicationContext applicationContext, Class<QUERY_HANDLER> type) {
        this.applicationContext = applicationContext;
        this.type = type;
    }

    public QUERY_HANDLER get() {
        return applicationContext.getBean(type);
    }
}
