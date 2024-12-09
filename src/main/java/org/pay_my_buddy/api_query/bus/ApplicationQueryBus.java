package org.pay_my_buddy.api_query.bus;

import org.pay_my_buddy.api_query.Query;
import org.pay_my_buddy.api_query.QueryBus;
import org.pay_my_buddy.api_query.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class ApplicationQueryBus implements QueryBus {

    private final QueryRegistry registry;

    public ApplicationQueryBus(QueryRegistry registry) {
        this.registry = registry;
    }

    @Override
    public <QUERY extends Query<RESPONSE>, RESPONSE> RESPONSE ask(QUERY query) {
        QueryHandler<QUERY, RESPONSE> queryHandler = (QueryHandler<QUERY, RESPONSE>) registry.get(query.getClass());
        return queryHandler.handle(query);
    }
}
