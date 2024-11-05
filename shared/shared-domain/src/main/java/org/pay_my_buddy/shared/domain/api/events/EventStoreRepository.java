package org.pay_my_buddy.shared.domain.api.events;


import org.pay_my_buddy.shared.domain.entity.EntityId;

import java.util.List;

public interface EventStoreRepository  {
    List<EventModel> findByAggregateIdentifier(EntityId aggregateIdentifier);

    void save(EventModel eventModel);

    List<EventModel>  findAll();
}
