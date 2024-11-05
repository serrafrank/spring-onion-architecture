package org.pay_my_buddy.user.domain.delete_user;

import org.pay_my_buddy.shared.domain.api.command.Command;
import org.pay_my_buddy.shared.domain.entity.EntityId;
import org.pay_my_buddy.shared.domain.validator.Constraint;

public record DeleteUserCommand(EntityId id) implements Command {

    public DeleteUserCommand {
        Constraint.checkIf(id).isNotNull("Id is required");
    }

}
