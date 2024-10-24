package org.pay_my_buddy.shared.infrastructure;

import org.pay_my_buddy.shared.domain.api.Query;
import org.pay_my_buddy.shared.domain.api.QueryMessageHandler;
import org.springframework.context.event.EventListener;

@FunctionalInterface
public interface QueryEventListener<QUERY extends Query> extends QueryMessageHandler<QUERY> {

    @EventListener
    default void onApplicationModuleEvent(QUERY query) {
        this.handle(query);
    }
}
