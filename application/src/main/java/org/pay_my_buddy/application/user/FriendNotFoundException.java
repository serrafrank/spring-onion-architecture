package org.pay_my_buddy.application.user;

import org.pay_my_buddy.entity.common.exception.UserNotFoundException;
import org.pay_my_buddy.entity.user.UserId;

public class FriendNotFoundException extends UserNotFoundException {

    public FriendNotFoundException(UserId friendId) {
        super("Friend not found with ID: %s", friendId);
    }
}
