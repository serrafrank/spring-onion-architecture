package org.pay_my_buddy.entity.user;

import org.pay_my_buddy.entity.exception.ResourceNotFoundException;

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

    public UserNotFoundException(Email email) {
        super("User not found with email: %s", email);
    }
}
