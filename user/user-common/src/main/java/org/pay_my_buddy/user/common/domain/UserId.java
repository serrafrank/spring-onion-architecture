package org.pay_my_buddy.user.common.domain;

import org.pay_my_buddy.shared.common.domain.entity.EntityId;

import java.util.UUID;

public class UserId extends EntityId {


    /**
     * Constructor that accepts a value for the ID.
     * The value is of type UUID.
     *
     * @param id The value of the ID.
     */
    private UserId(EntityId id) {
        super(id);
    }

    /**
     * Constructor that accepts a value for the ID.
     * The value is of type UUID.
     *
     * @param uuid The value of the ID.
     */
    private UserId(UUID uuid) {
        super(uuid);
    }

    private UserId(String id) {
        super(id);
    }

    /**
     * Default constructor that generates a unique ID.
     * It calls the generateUniqueId() method of the AbstractId class.
     * The value is of type UUID.
     */
    private UserId() {
        super();
    }


    public static UserId newId() {
        return new UserId();
    }

    public static UserId of(String id) {
        return new UserId(id);
    }

    public static UserId of(EntityId id) {
        return new UserId(id);
    }

    public static UserId of(UUID uuid) {
        return new UserId(uuid);
    }
}
