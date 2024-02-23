package com.example.paymybuddy.application.user.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateUserCommand(
        @NotBlank String email,
        @NotBlank @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR_MESSAGE) String password,
        @NotBlank String firstName,
        @NotBlank String lastName) {

    // digit + lowercase char + uppercase char + punctuation + symbol
    private static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final String PASSWORD_ERROR_MESSAGE = "Password must contain at least 8 characters, including 1 digit, 1 lowercase letter, 1 uppercase letter, and 1 special character";

}
