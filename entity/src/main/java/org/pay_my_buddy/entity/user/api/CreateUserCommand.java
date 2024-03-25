package org.pay_my_buddy.entity.user.api;


import org.apache.commons.lang3.StringUtils;
import org.pay_my_buddy.entity.commun.api.command.Command;
import org.pay_my_buddy.entity.commun.value_object.Email;
import org.pay_my_buddy.entity.commun.value_object.RawPassword;

public record CreateUserCommand(
        Email email,
        RawPassword password,
        String firstName,
        String lastName) implements Command {

    public CreateUserCommand {
        if (StringUtils.isBlank(firstName)) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }

        if (StringUtils.isBlank(lastName)) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
    }
}
