package org.pay_my_buddy.entity;

/**
 * This is an interface that represents a unique identifier (ID) for an entity.
 * The ID is of an Object.
 * It declares two methods: value() and generateUniqueId().
 */
public sealed interface Id extends ValueObject<Object> permits AbstractId {

    /**
     * Method to generate a unique ID.
     *
     * @return A unique ID.
     */
    Object generateUniqueId();
}