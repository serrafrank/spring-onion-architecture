package org.pay_my_buddy.api_command.event_storage;

import org.pay_my_buddy.api_command.Event;
import org.pay_my_buddy.shared.EntityId;

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
