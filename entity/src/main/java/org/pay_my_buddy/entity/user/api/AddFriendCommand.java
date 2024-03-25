package org.pay_my_buddy.entity.user.api;

import org.pay_my_buddy.entity.commun.api.command.Command;
import org.pay_my_buddy.entity.user.UserId;

public record AddFriendCommand(UserId userId, UserId friendId) implements Command {

    public AddFriendCommand {
        if (userId == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }

        if (friendId == null) {
            throw new IllegalArgumentException("Friend id cannot be null");
        }

        if (userId.equals(friendId)) {
            throw new IllegalArgumentException("User and friend cannot be the same");
        }
    }
}
