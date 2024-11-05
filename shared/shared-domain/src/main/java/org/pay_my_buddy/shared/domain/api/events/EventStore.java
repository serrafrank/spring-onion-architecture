package org.pay_my_buddy.shared.domain.api.events;

import org.pay_my_buddy.shared.domain.entity.EntityId;

import java.util.List;

public interface EventStore {
    void saveEvent(EntityId aggregateId, Class<?> aggregateClass, Iterable<Event> events, int expectedVersion);
    List<Event> getEvents(EntityId aggregateId);

    List<EntityId> getAggregateIds();
}