package org.pay_my_buddy.modules.user.shared.command;

import org.pay_my_buddy.core.framework.domain.message.Command;
import org.pay_my_buddy.core.framework.domain.validator.Validate;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.modules.user.shared.UserId;

public record AddFriendCommand(
        UserId userId,
        UserId friendId
) implements Command {

    public AddFriendCommand {
        Validate.checkIf(userId).isNotNull("User userId can't be null");
        Validate.checkIf(friendId).isNotNull("Friend userId can't be null");

        if (userId.equals(friendId)) {
            throw BusinessException.wrap(new IllegalArgumentException("User can't be friendId with himself"));
        }
    }
}
