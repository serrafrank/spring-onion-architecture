package com.example.paymybuddy.application.user.command;

import com.example.paymybuddy.core.validator.Constraint;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateUserCommand(
        @Email String email,
        @NotBlank @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR_MESSAGE) String password,
        @NotBlank String firstName,
        @NotBlank String lastName) {


    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String EMAIL_ERROR_MESSAGE = "Invalid email";

    // digit + lowercase char + uppercase char + punctuation + symbol
    private static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final String PASSWORD_ERROR_MESSAGE = "Password must contain at least 8 characters, including 1 digit, 1 lowercase letter, 1 uppercase letter, and 1 special character";

    public CreateUserCommand {
        Constraint.of(email).notBlank().matches(EMAIL_PATTERN, EMAIL_ERROR_MESSAGE).validate();
        Constraint.of(password).notBlank().matches(PASSWORD_PATTERN, PASSWORD_ERROR_MESSAGE).validate();
        Constraint.of(firstName).notBlank("First name cannot be null or empty").validate();
        Constraint.of(lastName).notBlank("Last name cannot be null or empty").validate();
    }
}
