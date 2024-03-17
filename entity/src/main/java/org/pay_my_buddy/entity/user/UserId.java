package org.pay_my_buddy.entity.user;

import org.pay_my_buddy.entity.commun.entity.GenericId;
import org.pay_my_buddy.entity.commun.entity.Id;

import java.util.UUID;

/**
 * UserId is a class that represents a unique identifier for a user in the system.
 * It extends the GenericUUID class.
 * This class is used to uniquely identify a user in the system.
 */
public class UserId extends GenericId {

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

    public static UserId of(Id id) {
        return new UserId(id);
    }

    public static UserId of(UUID uuid) {
        return new UserId(uuid);
    }
}