package org.pay_my_buddy.shared.exchange.user.command;

import org.pay_my_buddy.api_command.Command;
import org.pay_my_buddy.shared.Constraint;
import org.pay_my_buddy.shared.exchange.user.UserId;

public record AddFriendCommand(
        UserId userId,
        UserId friendId
) implements Command {

    public AddFriendCommand {
        Constraint.checkIf(userId).isNotNull("User id can't be null");
        Constraint.checkIf(friendId).isNotNull("Friend id can't be null");

        if(userId.equals(friendId)) {
            throw new IllegalArgumentException("User can't be friend with himself");
        }
    }
}
