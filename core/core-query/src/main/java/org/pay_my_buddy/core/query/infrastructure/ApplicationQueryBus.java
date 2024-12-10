package org.pay_my_buddy.core.query.infrastructure;

import org.pay_my_buddy.core.framework.domain.message.Query;
import org.pay_my_buddy.core.framework.application.QueryBus;
import org.pay_my_buddy.core.query.application.QueryHandler;
import org.pay_my_buddy.core.query.domain.bus.QueryRegistry;
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
