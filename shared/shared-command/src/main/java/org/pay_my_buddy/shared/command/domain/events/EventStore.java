package org.pay_my_buddy.shared.command.domain.events;

import org.pay_my_buddy.shared.common.domain.api.Event;
import org.pay_my_buddy.shared.common.domain.entity.EntityId;

import java.util.List;

public interface EventStore {
    void saveEvent(EntityId aggregateId, Class<?> aggregateClass, Iterable<Event> events, int expectedVersion);
    List<Event> getEvents(EntityId aggregateId);

    List<EntityId> getAggregateIds();
}
