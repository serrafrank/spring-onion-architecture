package org.pay_my_buddy.entity;

/**
 * This is an interface that represents an entity in the entity.
 * The entity has an ID of a generic type U which extends AbstractId.
 * It declares two methods: getId() and isNew().
 */
public interface Model<U extends AbstractId<?>> {

    /**
     * Method to get the ID of the entity.
     *
     * @return The ID of the entity. It can be null.
     */
    U id();

    /**
     * Method to check if the entity is new.
     * An entity is considered new if its ID is null.
     *
     * @return True if the entity is new, false otherwise.
     */
    default boolean isNew() {
        return this.id() == null;
    }
}