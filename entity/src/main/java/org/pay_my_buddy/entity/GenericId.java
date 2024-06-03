package org.pay_my_buddy.entity;

import java.util.UUID;

/**
 * This class extends the AbstractId class with UUID as the generic type.
 * It provides an implementation for the abstract method generateUniqueId() of the AbstractId class.
 * The generateUniqueId() method generates a random UUID.
 */
public class GenericId extends AbstractId<UUID> {

    /**
     * Constructor that accepts a value for the ID.
     * The value is of type UUID.
     *
     * @param value The value of the ID.
     */
    protected GenericId(UUID value) {
        super(value);
    }

    /**
     * Constructor that accepts a value for the ID.
     * The value is of type Id.
     *
     * @param id The value of the ID.
     */
    protected GenericId(Id id) {
        super(UUID.fromString(id.value().toString()));
    }

    /**
     * Default constructor that generates a unique ID.
     * It calls the generateUniqueId() method of the AbstractId class.
     * The value is of type UUID.
     */
    protected GenericId() {
        super();
    }

    /**
     * This method overrides the generateUniqueId() method of the AbstractId class.
     * It generates a random UUID.
     *
     * @return A random UUID.
     */
    public UUID generateUniqueId() {
        return UUID.randomUUID();
    }

    public static GenericId of() {
        return new GenericId();
    }

    public static GenericId of(Id id) {
        return new GenericId(id);
    }

    public static GenericId of(UUID uuid) {
        return new GenericId(uuid);
    }

}