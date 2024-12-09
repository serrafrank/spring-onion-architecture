package org.pay_my_buddy.shared.exchange.user.command;

import org.pay_my_buddy.api_command.Command;
import org.pay_my_buddy.shared.Constraint;
import org.pay_my_buddy.shared.exchange.user.UserId;

public record DeleteUserCommand(UserId id) implements Command {

    public DeleteUserCommand {
        Constraint.checkIf(id).isNotNull("Id is required");
    }

}
