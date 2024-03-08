package org.pay_my_buddy.entity.commun.entity;

/**
 * This is an interface that represents a unique identifier (ID) for an entity.
 * The ID is of an Object.
 * It declares two methods: value() and generateUniqueId().
 */
public interface Id {

    /**
     * Method to get the value of the ID.
     *
     * @return The value of the ID.
     */
    Object value();

    /**
     * Method to generate a unique ID.
     *
     * @return A unique ID.
     */
    Object generateUniqueId();
}