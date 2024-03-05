package com.example.paymybuddy.application.shared.entity.aggregate;

import com.example.paymybuddy.application.shared.entity.id.AbstractId;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * This is an interface that represents an entity in the domain.
 * The entity has an ID of a generic type U which extends AbstractId.
 * It extends the Serializable interface.
 * It declares two methods: getId() and isNew().
 */
public interface Entity<U extends AbstractId<?>> extends Serializable {

    /**
     * Method to get the ID of the entity.
     *
     * @return The ID of the entity. It can be null.
     */
    @Nullable
    U getId();

    /**
     * Method to check if the entity is new.
     * An entity is considered new if its ID is null.
     *
     * @return True if the entity is new, false otherwise.
     */
    default boolean isNew() {
        return this.getId() == null;
    }
}