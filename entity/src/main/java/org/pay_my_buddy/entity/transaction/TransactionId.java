package org.pay_my_buddy.entity.transaction;

import org.pay_my_buddy.entity.commun.entity.GenericId;
import org.pay_my_buddy.entity.commun.entity.Id;

import java.util.UUID;

public class TransactionId extends GenericId {

    /**
     * Constructor that accepts a value for the ID.
     * The value is of type UUID.
     *
     * @param id The value of the ID.
     */
    private TransactionId(Id id) {
        super(id);
    }

    /**
     * Constructor that accepts a value for the ID.
     * The value is of type UUID.
     *
     * @param uuid The value of the ID.
     */
    private TransactionId(UUID uuid) {
        super(uuid);
    }

    /**
     * Default constructor that generates a unique ID.
     * It calls the generateUniqueId() method of the AbstractId class.
     * The value is of type UUID.
     */
    private TransactionId() {
        super();
    }

    public static TransactionId of() {
        return new TransactionId();
    }

    public static TransactionId of(Id id) {
        return new TransactionId(id);
    }

    public static TransactionId of(UUID uuid) {
        return new TransactionId(uuid);
    }
}
