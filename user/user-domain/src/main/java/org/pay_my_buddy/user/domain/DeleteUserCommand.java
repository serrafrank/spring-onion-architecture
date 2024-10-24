package org.pay_my_buddy.user.domain;

import org.pay_my_buddy.shared.domain.api.Command;
import org.pay_my_buddy.shared.domain.entity.Id;
import org.pay_my_buddy.shared.domain.validator.Constraint;

public record DeleteUserCommand(Id id) implements Command {

    public DeleteUserCommand {
        Constraint.checkIf(id).notNull("Id is required");
    }

}
