package org.pay_my_buddy.entity.common.entity;

import lombok.experimental.SuperBuilder;

/**
 * AbstractModel is an abstract class that represents a persistent entity in the system.
 * It implements the Entity interface.
 * It contains the id of the entity and provides a method to get this id.
 *
 * @param <U> The type of the id of the entity.
 * @author serrafrank
 * @version 1.0
 * @since 2023.3.3
 */
@SuperBuilder
public abstract class AbstractModel<U extends AbstractId<?>> implements Model<U> {
    /**
     * The id of the entity.
     */
    protected final U id;

    /**
     * Constructor for the AbstractModel class.
     *
     * @param id The id of the entity.
     */
    protected AbstractModel(U id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        this.id = id;
    }

    protected AbstractModel() {
        this.id = null;
    }

    /**
     * Returns the id of the entity.
     *
     * @return The id of the entity.
     */
    public U getId() {
        return this.id;
    }

}