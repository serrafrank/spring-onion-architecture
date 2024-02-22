package com.example.paymybuddy.core.common.entity.aggregate;

import com.example.paymybuddy.core.common.entity.id.AbstractId;
import org.springframework.lang.Nullable;

/**
 * AbstractEntity is an abstract class that represents a persistent entity in the system.
 * It implements the Entity interface.
 * It contains the id of the entity and provides a method to get this id.
 *
 * @param <U> The type of the id of the entity.
 * @author serrafrank
 * @version 1.0
 * @since 2023.3.3
 */
public abstract class AbstractEntity<U extends AbstractId<?>> implements Entity<U> {
    /**
     * The id of the entity.
     */
    private final U id;

    /**
     * Constructor for the AbstractEntity class.
     *
     * @param id The id of the entity.
     */
    protected AbstractEntity(U id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        this.id = id;
    }

    protected AbstractEntity() {
        this.id = null;
    }

    /**
     * Returns the id of the entity.
     *
     * @return The id of the entity.
     */
    @Nullable
    public U getId() {
        return this.id;
    }
}