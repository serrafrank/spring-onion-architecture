package org.pay_my_buddy.api_command;

public interface EventSourcingStorage<AGGREGATE, ID> {
    void save(AbstractAggregateRoot<?> aggregate);

    AGGREGATE getById(ID aggregateId);

    void republishEvents();
}
