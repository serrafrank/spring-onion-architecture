package com.example.paymybuddy.core.common.entity.id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * This is an abstract class that represents a unique identifier (ID) for an entity.
 * The ID is of a generic type U which extends Serializable.
 * This class implements the Id interface and Serializable interface.
 * It uses Lombok annotations for getter, equals and hashcode, and toString methods.
 */
@Getter
@EqualsAndHashCode
@ToString
public abstract class AbstractId<U extends Serializable> implements Id, Serializable {

    // The value of the ID.
    private final U value;

    /**
     * Constructor that accepts a value for the ID.
     *
     * @param value The value of the ID.
     */
    protected AbstractId(U value) {
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
    @Override
    public abstract U generateUniqueId();

}