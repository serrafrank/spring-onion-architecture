package org.pay_my_buddy.entity.account;

import org.pay_my_buddy.entity.common.entity.GenericId;
import org.pay_my_buddy.entity.common.entity.Id;

import java.util.UUID;

/**
 * UserId is a class that represents a unique identifier for a user in the system.
 * It extends the GenericUUID class.
 * This class is used to uniquely identify a user in the system.
 */
public class AccountId extends GenericId {

    /**
     * Constructor that accepts a value for the ID.
     * The value is of type UUID.
     *
     * @param id The value of the ID.
     */
    private AccountId(Id id) {
        super(id);
    }

    /**
     * Constructor that accepts a value for the ID.
     * The value is of type UUID.
     *
     * @param uuid The value of the ID.
     */
    private AccountId(UUID uuid) {
        super(uuid);
    }

    /**
     * Default constructor that generates a unique ID.
     * It calls the generateUniqueId() method of the AbstractId class.
     * The value is of type UUID.
     */
    private AccountId() {
        super();
    }

    @Override
    public String toString() {
        return value().toString();
    }

    public static AccountId of() {
        return new AccountId();
    }

    public static AccountId of(Id id) {
        return new AccountId(id);
    }

    public static AccountId of(UUID uuid) {
        return new AccountId(uuid);
    }
}