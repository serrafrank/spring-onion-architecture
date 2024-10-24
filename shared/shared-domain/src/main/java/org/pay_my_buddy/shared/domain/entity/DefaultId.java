package org.pay_my_buddy.shared.domain.entity;

import java.util.UUID;

/**
 * This class extends the AbstractId class with UUID as the generic type.
 * It provides an implementation for the abstract method generateUniqueId() of the AbstractId class.
 * The generateUniqueId() method generates a random UUID.
 */
public class DefaultId implements Id {

    // The value of the ID.
    private final UUID value;

    @Override
    public UUID value() {
        return value;
    }

    protected DefaultId() {
        this(UUID.randomUUID());
    }

    /**
     * Constructor that accepts a value for the ID.
     * The value is of type UUID.
     *
     * @param value The value of the ID.
     */
    protected DefaultId(UUID value) {
        this.value = value;
    }

    /**
     * Constructor that accepts a value for the ID.
     * The value is of type Id.
     *
     * @param id The value of the ID.
     */
    protected DefaultId(Id id) {
        this(UUID.fromString(id.value().toString()));
    }


    protected DefaultId(String id) {
        this(UUID.fromString(id));
    }



    public static DefaultId of() {
        return new DefaultId();
    }

    public static DefaultId of(Id id) {
        return new DefaultId(id);
    }

    public static DefaultId of(String id) {
        return new DefaultId(id);
    }

    public static DefaultId of(UUID uuid) {
        return new DefaultId(uuid);
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

    @Override
    public String toString() {
        return value().toString();
    }

}