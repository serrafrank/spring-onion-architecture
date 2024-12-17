package org.pay_my_buddy.core.command.infrastructure.event_sourcing;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.pay_my_buddy.core.command.domain.ObjectConverter;
import org.pay_my_buddy.core.command.domain.event_storage.EventWrapper;
import org.pay_my_buddy.core.framework.domain.message.Event;
import org.pay_my_buddy.core.framework.domain.message.EventId;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;

import java.time.LocalDateTime;

@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(fluent = true)
public class EventWrapperEntity implements EventWrapper {
    @Id
    private String eventId;
    private LocalDateTime timestamp;
    private int index;
    private String aggregateId;
    private String aggregateType;
    private String eventType;
    @Lob
    private String eventData;

    public EventWrapperEntity(EventWrapper eventWrapper) {
        this.eventId = eventWrapper.eventId().value();
        this.timestamp = eventWrapper.timestamp();
        this.index = eventWrapper.index();
        this.aggregateId = eventWrapper.aggregateId().value();
        this.aggregateType = eventWrapper.aggregateType();
        this.eventType = eventWrapper.eventType();
        this.eventData = ObjectConverter.toJson(eventWrapper.event());
    }

    @Override
    public EntityId aggregateId() {
        return EntityId.of(aggregateId);
    }

    @Override
    public EventId eventId() {
        return new EventId(eventId);
    }

    @Override
    public Event event() {
        return ObjectConverter.fromJson(eventData, eventType);
    }
}
