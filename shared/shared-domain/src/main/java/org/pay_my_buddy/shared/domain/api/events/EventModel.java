package org.pay_my_buddy.shared.domain.api.events;

import org.pay_my_buddy.shared.domain.entity.EntityId;

import java.time.LocalDateTime;

public interface EventModel {

    EntityId id ();
    LocalDateTime timestamp ();
    EntityId aggregateIdentifier();
    String aggregateType();
    int version();
    String eventType();
    Event eventData();

}
