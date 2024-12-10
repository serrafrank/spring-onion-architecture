package org.pay_my_buddy.core.command.domain.event_storage;

import java.time.LocalDateTime;
import org.pay_my_buddy.core.framework.domain.message.Event;
import org.pay_my_buddy.core.framework.domain.message.EventId;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;

public interface EventWrapper {
    EventId eventId();

    LocalDateTime timestamp();

    int index();

    EntityId aggregateId();

    String aggregateType();

    String eventType();

    Event event();

}
