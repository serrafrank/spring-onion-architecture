package org.pay_my_buddy.api_command;


import org.pay_my_buddy.shared.EntityId;

public interface EventSourcingHandler<AGGREGATE extends AggregateRoot<?, ?>> {
    void save(AggregateRoot<?, ?> aggregate);
    AGGREGATE getById(EntityId id);


    void republishEvents();
}
