package org.pay_my_buddy.api_command.event_storage;


import org.pay_my_buddy.shared.EntityId;

import java.util.List;
import java.util.Set;

public interface EventStoreRepository  {
    List<EventModel> findByAggregateIdentifier(EntityId aggregateIdentifier);

    void save(EventModel eventModel);

    List<EventModel>  findAll();

    Set<EntityId> findAllAggregateIds();
}
