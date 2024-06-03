package org.pay_my_buddy.entity.exception;

import org.pay_my_buddy.entity.exception.generic.ResourceNotFoundException;
import org.pay_my_buddy.entity.user.UserId;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(UserId id) {
        super("User not found with ID: %s", id);
    }

    public UserNotFoundException(String message, UserId id) {
        super(message, id);
    }
}
