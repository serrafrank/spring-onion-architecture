package org.pay_my_buddy.api_command.event_storage;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.pay_my_buddy.api_command.Event;
import org.pay_my_buddy.shared.EntityId;

import java.time.Clock;
import java.time.LocalDateTime;

@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class EventModelEntity implements EventModel {

    @Id
    private EntityId id;
    private LocalDateTime timestamp;
    private EntityId aggregateIdentifier;
    private String aggregateType;
    private int version;
    private String eventType;

    @Lob()
    private Event eventData;

    public EventModelEntity(EntityId aggregateIdentifier, String aggregateType, int version, String eventType, Event eventData) {
        this.id = EntityId.createRandomUnique();
        this.timestamp = LocalDateTime.now(Clock.systemUTC());
        this.aggregateIdentifier = aggregateIdentifier;
        this.aggregateType = aggregateType;
        this.version = version;
        this.eventType = eventType;
        this.eventData = eventData;

    }

    public EventModelEntity(EventModel eventModel) {
        this(eventModel.aggregateIdentifier(),eventModel.aggregateType(), eventModel.version(), eventModel.eventType(), eventModel.eventData());
    }

    @Override
    public EntityId id() {
        return EntityId.of(id);
    }

    @Override
    public EntityId aggregateIdentifier() {
        return EntityId.of(aggregateIdentifier);
    }

}