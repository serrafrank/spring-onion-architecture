package org.pay_my_buddy.application.use_case.user.command.add_friend;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.pay_my_buddy.application.common.api.AbstractCommand;
import org.pay_my_buddy.entity.user.UserId;

@Value
@EqualsAndHashCode(callSuper = true)
public class AddFriendCommand extends AbstractCommand<Void> {
    UserId userId;
    UserId friendId;

    private AddFriendCommand(UserId userId, UserId friendId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }

        if (friendId == null) {
            throw new IllegalArgumentException("Friend id cannot be null");
        }

        if (userId.equals(friendId)) {
            throw new IllegalArgumentException("User and friend cannot be the same");
        }
        this.userId = userId;
        this.friendId = friendId;
    }

    public static AddFriendCommand of(UserId userId, UserId friendId) {
        return new AddFriendCommand(userId, friendId);
    }

}
