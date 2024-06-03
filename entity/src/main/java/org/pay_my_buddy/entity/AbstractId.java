package org.pay_my_buddy.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * This is an abstract class that represents a unique identifier (ID) for an entity.
 * It uses Lombok annotations for getter, equals and hashcode, and toString methods.
 */
@Getter
@EqualsAndHashCode
public abstract class AbstractId<U> implements Id {

    // The value of the ID.
    private final U value;

    /**
     * Constructor that accepts a value for the ID.
     *
     * @param value The value of the ID.
     */
    protected AbstractId(U value) {
        if (value == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        this.value = value;
    }

    /**
     * Default constructor that generates a unique ID.
     */
    protected AbstractId() {
        this.value = generateUniqueId();
    }

    /**
     * Method to get the value of the ID.
     *
     * @return The value of the ID.
     */
    @Override
    public U value() {
        return value;
    }

    /**
     * Abstract method to generate a unique ID.
     * This method needs to be implemented by subclasses.
     *
     * @return A unique ID.
     */
    public abstract U generateUniqueId();

    @Override
    public String toString() {
        return value().toString();
    }
}