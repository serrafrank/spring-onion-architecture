package com.example.paymybuddy.application.user.domain;

import com.example.paymybuddy.application.shared.entity.id.GenericId;
import com.example.paymybuddy.application.shared.entity.id.Id;

/**
 * UserId is a class that represents a unique identifier for a user in the system.
 * It extends the GenericUUID class.
 * This class is used to uniquely identify a user in the system.
 *
 * @author serrafrank
 * @version 1.0
 * @since 2023.3.3
 */
public class UserId extends GenericId {
    public UserId(Id id) {
        super(id);
    }

    public UserId() {
        super();
    }
}