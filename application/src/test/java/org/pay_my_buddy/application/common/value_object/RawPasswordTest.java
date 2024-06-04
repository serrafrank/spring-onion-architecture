package org.pay_my_buddy.application.common.value_object;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.pay_my_buddy.entity.user.RawPassword;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class RawPasswordTest {

    private static Stream<Arguments> invalidPassword() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("1234567"), // less than 8 characters
                Arguments.of("123456789012345678901"), // more than 30 characters
                Arguments.of("password"), // no upper case letter
                Arguments.of("PASSWORD"), // no lower case letter
                Arguments.of("!@#$%^&*"), // no number
                Arguments.of("1abZRplYU") // no special character
        );
    }

    private static Stream<Arguments> validPassword() {

        return Stream.of(
                Arguments.of("Password123!"),
                Arguments.of("AnotherValidPassword1!")
        );
    }


    @ParameterizedTest
    @DisplayName("Creating a RawPassword object with an invalid password should throw an IllegalArgumentException")
    @MethodSource("invalidPassword")
    void create_raw_password_with_invalid_password_should_throw_IllegalArgumentException(String invalidPassword) {
        assertThrows(IllegalArgumentException.class, () -> RawPassword.of(invalidPassword));
    }

    @ParameterizedTest
    @DisplayName("Creating a RawPassword object with a valid password should succeed")
    @MethodSource("validPassword")
    void create_raw_password_with_valid_password_should_succeed(String validPassword) {
        RawPassword rawPassword = RawPassword.of(validPassword);
        assertNotNull(rawPassword);
        assertEquals(validPassword, rawPassword.toString());
    }
}