package com.example.paymybuddy.core.common.entity.id;

import java.util.UUID;

/**
 * This class extends the AbstractId class with UUID as the generic type.
 * It provides an implementation for the abstract method generateUniqueId() of the AbstractId class.
 * The generateUniqueId() method generates a random UUID.
 */
public class GenericUUID extends AbstractId<UUID> {

    /**
     * This method overrides the generateUniqueId() method of the AbstractId class.
     * It generates a random UUID.
     *
     * @return A random UUID.
     */
    public UUID generateUniqueId() {
        return UUID.randomUUID();
    }
}