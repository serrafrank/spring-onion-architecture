package org.pay_my_buddy.user.domain;

import org.pay_my_buddy.shared.domain.api.Command;
import org.pay_my_buddy.shared.domain.validator.Constraint;

public record CreateUserCommand(UserId id,
                                String firstname,
                                String lastname,
                                String email,
                                String password) implements Command {

    public CreateUserCommand {
        Constraint.checkIf(id).notNull("Id is required");
        Constraint.checkIf(firstname).notBlank("firstname can not be blank");
        Constraint.checkIf(lastname).notBlank("lastname can not be blank");
        Constraint.checkIf(email)
                .notBlank("email can not be blank")
                .email();
        Constraint.checkIf(password).notBlank("password is required");
    }


    public CreateUserCommand(String firstname, String lastname, String email, String password) {
        this(UserId.of(), firstname, lastname, email, password);
    }

}
