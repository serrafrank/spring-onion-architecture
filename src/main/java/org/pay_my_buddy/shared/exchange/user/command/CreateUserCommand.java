package org.pay_my_buddy.shared.exchange.user.command;

import org.pay_my_buddy.api_command.Command;
import org.pay_my_buddy.shared.Constraint;

public record CreateUserCommand(String firstname,
								String lastname,
								String email,
								String password) implements Command {

	public CreateUserCommand {
		Constraint.checkIf(firstname).notBlank("firstname can not be blank");
		Constraint.checkIf(lastname).notBlank("lastname can not be blank");
		Constraint.checkIf(email)
				.notBlank("email can not be blank")
				.email();
		Constraint.checkIf(password).notBlank("password is required");
	}

}
