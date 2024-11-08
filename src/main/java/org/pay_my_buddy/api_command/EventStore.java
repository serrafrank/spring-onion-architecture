package org.pay_my_buddy.api_command;

import org.pay_my_buddy.shared.EntityId;

import java.util.List;
import java.util.Set;

public interface EventStore {
    void saveEvent(EntityId aggregateId, Class<?> aggregateClass, Iterable<Event> events, int expectedVersion);
    List<Event> getEvents(EntityId aggregateId);

    Set<? extends EntityId> getAggregateIds();
}
