package org.pay_my_buddy.modules.user.shared.command;

import org.pay_my_buddy.core.framework.domain.message.Command;
import org.pay_my_buddy.core.framework.domain.validator.Validate;

public record CreateUserCommand(String firstname,
                                String lastname,
                                String email,
                                String password) implements Command {

    public CreateUserCommand {
        Validate.checkIf(firstname).notBlank("firstname can not be blank");
        Validate.checkIf(lastname).notBlank("lastname can not be blank");
        Validate.checkIf(email)
                .notBlank("email can not be blank")
                .email();
        Validate.checkIf(password).notBlank("password is required");
    }

}
