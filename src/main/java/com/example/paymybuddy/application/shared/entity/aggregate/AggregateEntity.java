package com.example.paymybuddy.application.shared.entity.aggregate;

import com.example.paymybuddy.application.shared.entity.id.AbstractId;
import com.example.paymybuddy.application.shared.entity.id.Id;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * AggregateEntity is an interface that represents an aggregate entity in the system.
 * It extends the IdentifiableEntity interface.
 * It contains methods to get and set the user who created and last modified the entity,
 * and the time when the entity was created and last modified.
 *
 * @param <U> The type of the id of the entity.
 * @author serrafrank
 * @version 1.0
 * @since 2023.3.3
 */
public interface AggregateEntity<U extends AbstractId<?>> extends Entity<U> {

    /**
     * Returns the user who created the entity.
     *
     * @return The user who created the entity.
     */
    Optional<Id> getCreatedBy();

    /**
     * Sets the user who created the entity.
     *
     * @param createdBy The user who created the entity.
     */
    void setCreatedBy(Id createdBy);

    /**
     * Returns the time when the entity was created.
     *
     * @return The time when the entity was created.
     */
    Optional<OffsetDateTime> getCreatedAt();

    /**
     * Sets the time when the entity was created.
     *
     * @param creationDate The time when the entity was created.
     */
    void setCreatedAt(OffsetDateTime creationDate);

    /**
     * Sets the time when the entity was created.
     *
     * @param creationDate The time when the entity was created.
     */
    void setCreatedDate(LocalDateTime creationDate);

    /**
     * Returns the user who last modified the entity.
     *
     * @return The user who last modified the entity.
     */
    Optional<Id> getLastModifiedBy();

    /**
     * Sets the user who last modified the entity.
     *
     * @param lastModifiedBy The user who last modified the entity.
     */
    void setLastModifiedBy(Id lastModifiedBy);

    /**
     * Returns the time when the entity was last modified.
     *
     * @return The time when the entity was last modified.
     */
    Optional<OffsetDateTime> getLastModifiedAt();

    /**
     * Sets the time when the entity was last modified.
     *
     * @param lastModifiedAt The time when the entity was last modified.
     */
    void setLastModifiedAt(OffsetDateTime lastModifiedAt);

    /**
     * Sets the time when the entity was last modified.
     *
     * @param lastModifiedDate The time when the entity was last modified.
     */
    void setLastModifiedDate(LocalDateTime lastModifiedDate);
}