package org.pay_my_buddy.entity.application.user;

import org.pay_my_buddy.entity.exception.generic.IllegalRequestException;
import org.pay_my_buddy.entity.user.UserId;

public class UserIsAlreadyAFriendException extends IllegalRequestException {

    public UserIsAlreadyAFriendException() {
        super("User is already a friend");
    }

    public UserIsAlreadyAFriendException(String message) {
        super(message);
    }

    public UserIsAlreadyAFriendException(UserId id) {
        super("User with ID: %s is already in the user's friend list", id);
    }
}
