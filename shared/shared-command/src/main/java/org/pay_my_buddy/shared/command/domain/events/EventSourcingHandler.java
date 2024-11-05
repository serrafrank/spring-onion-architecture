package org.pay_my_buddy.shared.command.domain.events;


import org.pay_my_buddy.shared.command.domain.AggregateRoot;
import org.pay_my_buddy.shared.common.domain.entity.EntityId;

public interface EventSourcingHandler<AGGREGATE extends AggregateRoot<?>> {
    void save(AGGREGATE aggregate);
    AGGREGATE getById(EntityId id);


    void republishEvents();
}
