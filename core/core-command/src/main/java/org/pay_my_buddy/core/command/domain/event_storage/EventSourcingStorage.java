package org.pay_my_buddy.core.command.domain.event_storage;

import org.pay_my_buddy.core.command.domain.AbstractAggregateRoot;

public interface EventSourcingStorage<AGGREGATE, ID> {
    void save(AbstractAggregateRoot<?> aggregate);

    AGGREGATE getById(ID aggregateId);

    void republishEvents();
}
