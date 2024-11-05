package org.pay_my_buddy.user.domain.add_friend;

import org.pay_my_buddy.shared.domain.api.command.Command;
import org.pay_my_buddy.shared.domain.entity.EntityId;
import org.pay_my_buddy.shared.domain.validator.Constraint;

public record AddFriendCommand(
        EntityId userId,
        EntityId friendId
) implements Command {

    public AddFriendCommand {
        Constraint.checkIf(userId).isNotNull("User id can't be null");
        Constraint.checkIf(friendId).isNotNull("Friend id can't be null");

        if(userId.equals(friendId)) {
            throw new IllegalArgumentException("User can't be friend with himself");
        }
    }
}
