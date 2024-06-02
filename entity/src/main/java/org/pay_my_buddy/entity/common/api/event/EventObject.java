package org.pay_my_buddy.entity.common.api.event;

import org.pay_my_buddy.entity.common.entity.Id;

import java.time.LocalDateTime;

public class EventObject implements Event {

    private final Id eventId;
    private final LocalDateTime occurredOn;

    @Override
    public Id eventId() {
        return eventId;
    }

    @Override
    public LocalDateTime occurredOn() {
        return occurredOn;
    }

    protected EventObject() {
        this(EventId.of(), LocalDateTime.now());
    }

    protected EventObject(Id eventId, LocalDateTime occurredOn) {
        this.eventId = eventId;
        this.occurredOn = occurredOn;
    }
}
