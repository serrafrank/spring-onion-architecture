package org.pay_my_buddy.entity.user.api;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.pay_my_buddy.entity.commun.api.command.Command;

public record CreateUserCommand(
        String email,
        String password,
        String firstName,
        String lastName) implements Command {


    private static final EmailValidator emailValidator = EmailValidator.getInstance();
    private static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final String PASSWORD_ERROR_MESSAGE = "Password must contain at least 8 characters, including 1 digit, 1 lowercase letter, 1 uppercase letter, and 1 special character";

    public CreateUserCommand {
        if(!emailValidator.isValid(email)) {
            throw new IllegalArgumentException("Invalid email");
        }

        if (!password.matches(PASSWORD_PATTERN)) {
            throw new IllegalArgumentException(PASSWORD_ERROR_MESSAGE);
        }

        if (StringUtils.isBlank(firstName)) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }

        if (StringUtils.isBlank(lastName)) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
    }
}
