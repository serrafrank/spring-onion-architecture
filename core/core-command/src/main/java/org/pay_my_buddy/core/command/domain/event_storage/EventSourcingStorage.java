package org.pay_my_buddy.core.command.domain.event_storage;


public interface EventSourcingStorage<AGGREGATE, ID> {
    AGGREGATE save(AGGREGATE aggregate);

    AGGREGATE getById(ID aggregateId);

    void republishEvents();
}
