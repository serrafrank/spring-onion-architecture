package com.example.paymybuddy.application.shared.entity.aggregate;

import com.example.paymybuddy.application.shared.entity.id.AbstractId;
import com.example.paymybuddy.application.shared.entity.id.Id;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;

/**
 * AbstractAggregateEntity is a class that represents an auditable entity in the system.
 * It extends the AbstractEntity class and implements the AuditableEntity interface.
 * It contains information about the entity such as createdBy, createdAt, lastModifiedBy, and lastModifiedAt.
 * It also provides methods to get and set these values.
 *
 * @param <U> The type of the id of the entity.
 * @author serrafrank
 * @version 1.0
 * @since 2023.3.3
 */
public class AbstractAggregateEntity<U extends AbstractId<?>> extends AbstractEntity<U> implements AggregateEntity<U> {
    /**
     * The user who created the entity.
     */
    private Id createdBy;

    /**
     * The time when the entity was created.
     */
    private OffsetDateTime createdAt;

    /**
     * The user who last modified the entity.
     */
    private Id lastModifiedBy;

    /**
     * The time when the entity was last modified.
     */
    private OffsetDateTime lastModifiedAt;

    /**
     * Constructor for the AbstractAggregateEntity class.
     *
     * @param id The id of the entity.
     */
    protected AbstractAggregateEntity(U id) {
        super(id);
    }

    protected AbstractAggregateEntity() {
        super();
    }

    /**
     * Returns the user who created the entity.
     *
     * @return The user who created the entity.
     */
    @Override
    public Optional<Id> getCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    /**
     * Sets the user who created the entity.
     *
     * @param createdBy The user who created the entity.
     */
    @Override
    public void setCreatedBy(Id createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Returns the time when the entity was created.
     *
     * @return The time when the entity was created.
     */
    @Override
    public Optional<OffsetDateTime> getCreatedAt() {
        return Optional.ofNullable(createdAt);
    }

    /**
     * Sets the time when the entity was created.
     *
     * @param creationDate The time when the entity was created.
     */
    @Override
    public void setCreatedAt(OffsetDateTime creationDate) {
        this.createdAt = creationDate;
    }

    /**
     * Sets the time when the entity was created.
     *
     * @param creationDate The time when the entity was created.
     */
    @Override
    public void setCreatedDate(LocalDateTime creationDate) {
        this.createdAt = this.toOffsetDateTime(creationDate);
    }

    /**
     * Returns the user who last modified the entity.
     *
     * @return The user who last modified the entity.
     */
    @Override
    public Optional<Id> getLastModifiedBy() {
        return Optional.ofNullable(lastModifiedBy);
    }

    /**
     * Sets the user who last modified the entity.
     *
     * @param lastModifiedBy The user who last modified the entity.
     */
    @Override
    public void setLastModifiedBy(@Nullable Id lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * Returns the time when the entity was last modified.
     *
     * @return The time when the entity was last modified.
     */
    @Override
    public Optional<OffsetDateTime> getLastModifiedAt() {
        return Optional.ofNullable(lastModifiedAt);
    }

    /**
     * Sets the time when the entity was last modified.
     *
     * @param lastModifiedAt The time when the entity was last modified.
     */
    @Override
    public void setLastModifiedAt(OffsetDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    /**
     * Sets the time when the entity was last modified.
     *
     * @param lastModifiedDate The time when the entity was last modified.
     */
    @Override
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedAt = this.toOffsetDateTime(lastModifiedDate);
    }

    /**
     * Method to convert a LocalDateTime to an OffsetDateTime.
     *
     * @param localDateTime The LocalDateTime to convert.
     * @return The converted OffsetDateTime.
     */
    private OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atZone(ZoneId.systemDefault()).toOffsetDateTime();
    }
}