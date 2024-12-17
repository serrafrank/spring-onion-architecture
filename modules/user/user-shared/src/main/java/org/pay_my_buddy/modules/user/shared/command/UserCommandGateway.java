package org.pay_my_buddy.modules.user.shared.command;

import org.pay_my_buddy.core.framework.domain.Gateway;
import org.pay_my_buddy.modules.user.shared.UserId;

public interface UserCommandGateway extends Gateway {

    void handle(AddFriendCommand command);

    UserId handle(CreateUserCommand command);

    void handle(DeleteUserCommand command);

}
