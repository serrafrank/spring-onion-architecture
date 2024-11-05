package org.pay_my_buddy.user.common.domain;

import org.pay_my_buddy.shared.common.domain.api.command.Command;
import org.pay_my_buddy.shared.common.domain.entity.EntityId;
import org.pay_my_buddy.shared.common.domain.validator.Constraint;

public record DeleteUserCommand(EntityId id) implements Command {

    public DeleteUserCommand {
        Constraint.checkIf(id).isNotNull("Id is required");
    }

}
