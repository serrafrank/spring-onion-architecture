package org.pay_my_buddy.core.query.domain.bus;

import org.pay_my_buddy.core.query.application.QueryHandler;
import org.springframework.context.ApplicationContext;

@SuppressWarnings("unchecked")
public class QueryProvider<QUERY_HANDLER extends QueryHandler<?, ?>> {

    private final ApplicationContext applicationContext;
    private final Class<QUERY_HANDLER> type;

    public QueryProvider(ApplicationContext applicationContext, Class<QUERY_HANDLER> type) {
        this.applicationContext = applicationContext;
        this.type = type;
    }

    public QUERY_HANDLER get() {
        return applicationContext.getBean(type);
    }
}
