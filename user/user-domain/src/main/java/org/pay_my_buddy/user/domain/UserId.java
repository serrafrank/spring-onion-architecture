package org.pay_my_buddy.user.domain;

import org.pay_my_buddy.shared.domain.entity.DefaultId;
import org.pay_my_buddy.shared.domain.entity.Id;

import java.util.UUID;

public class UserId extends DefaultId {


    /**
     * Constructor that accepts a value for the ID.
     * The value is of type UUID.
     *
     * @param id The value of the ID.
     */
    private UserId(Id id) {
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


    public static UserId of() {
        return new UserId();
    }

    public static UserId of(String id) {
        return new UserId(id);
    }

    public static UserId of(Id id) {
        return new UserId(id);
    }

    public static UserId of(UUID uuid) {
        return new UserId(uuid);
    }
}