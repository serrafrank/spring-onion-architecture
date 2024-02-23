package com.example.paymybuddy.core.user.valueobject;

import com.example.paymybuddy.core.common.entity.id.GenericUUID;
import com.example.paymybuddy.core.common.entity.id.Id;

/**
 * UserId is a class that represents a unique identifier for a user in the system.
 * It extends the GenericUUID class.
 * This class is used to uniquely identify a user in the system.
 *
 * @author serrafrank
 * @version 1.0
 * @since 2023.3.3
 */
public class UserId extends GenericUUID {
    public UserId(Id id) {
        super(id);
    }

    public UserId() {
        super();
    }
}