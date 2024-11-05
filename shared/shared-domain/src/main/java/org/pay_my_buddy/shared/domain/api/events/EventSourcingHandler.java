package org.pay_my_buddy.shared.domain.api.events;


import org.pay_my_buddy.shared.domain.entity.AggregateRoot;
import org.pay_my_buddy.shared.domain.entity.EntityId;

public interface EventSourcingHandler<AGGREGATE extends AggregateRoot<?>> {
    void save(AGGREGATE aggregate);
    AGGREGATE getById(EntityId id);


    void republishEvents();
}
