package org.pay_my_buddy.shared.command.domain.events;

import org.pay_my_buddy.shared.common.domain.api.Event;
import org.pay_my_buddy.shared.common.domain.entity.EntityId;

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
