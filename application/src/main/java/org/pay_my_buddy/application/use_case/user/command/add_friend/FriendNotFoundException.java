package org.pay_my_buddy.application.use_case.user.command.add_friend;

import org.pay_my_buddy.entity.user.UserId;
import org.pay_my_buddy.entity.user.UserNotFoundException;

public class FriendNotFoundException extends UserNotFoundException {

    public FriendNotFoundException(UserId friendId) {
        super("Friend not found with ID: %s", friendId);
    }
}
