package com.example.paymybuddy.application.user.command;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateUserCommandTest {

    @ParameterizedTest
    @ValueSource(strings = {"test@example.com", "test2@example.com", "test3@example.com"})
    void shouldCreateUserCommandWithValidEmail(String email) {
        assertDoesNotThrow(() -> new CreateUserCommand(email, "Password1!", "John", "Doe"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "test", "test@", "test@.com"})
    void shouldThrowExceptionWhenEmailIsInvalid(String email) {
        assertThrows(IllegalArgumentException.class, () -> new CreateUserCommand(email, "Password1!", "John", "Doe"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Password1!", "Password2@", "Password3#"})
    void shouldCreateUserCommandWithValidPassword(String password) {
        assertDoesNotThrow(() -> new CreateUserCommand("test@example.com", password, "John", "Doe"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"password!", "PASSWORD1!", "Password1", "Pass1!", "Password1!Password1!Password1!"})
    void shouldThrowExceptionWhenPasswordIsInvalid(String password) {
        assertThrows(IllegalArgumentException.class, () -> new CreateUserCommand("test@example.com", password, "John", "Doe"));
    }
}