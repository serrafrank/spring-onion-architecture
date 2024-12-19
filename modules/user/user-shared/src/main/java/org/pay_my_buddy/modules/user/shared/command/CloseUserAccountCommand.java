package org.pay_my_buddy.modules.user.shared.command;

import org.pay_my_buddy.core.framework.domain.message.Command;
import org.pay_my_buddy.core.framework.domain.validator.Validate;
import org.pay_my_buddy.modules.user.shared.UserId;

public record CloseUserAccountCommand(UserId id) implements Command {

    public CloseUserAccountCommand {
        Validate.checkIf(id).isNotNull("Id is required");
    }

}
