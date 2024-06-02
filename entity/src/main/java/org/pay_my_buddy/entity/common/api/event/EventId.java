package org.pay_my_buddy.entity.common.api.event;

import org.pay_my_buddy.entity.common.entity.GenericId;
import org.pay_my_buddy.entity.common.entity.Id;

import java.util.UUID;

public class EventId extends GenericId {

    /**
     * Constructor that accepts a value for the ID.
     * The value is of type UUID.
     *
     * @param id The value of the ID.
     */
    private EventId(Id id) {
        super(id);
    }

    /**
     * Constructor that accepts a value for the ID.
     * The value is of type UUID.
     *
     * @param uuid The value of the ID.
     */
    private EventId(UUID uuid) {
        super(uuid);
    }

    /**
     * Default constructor that generates a unique ID.
     * It calls the generateUniqueId() method of the AbstractId class.
     * The value is of type UUID.
     */
    private EventId() {
        super();
    }

    @Override
    public String toString() {
        return value().toString();
    }

    public static EventId of() {
        return new EventId();
    }

    public static EventId of(Id id) {
        return new EventId(id);
    }

    public static EventId of(UUID uuid) {
        return new EventId(uuid);
    }
}