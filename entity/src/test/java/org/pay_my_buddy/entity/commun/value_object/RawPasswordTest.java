package org.pay_my_buddy.entity.commun.value_object;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RawPasswordTest {

    @ParameterizedTest
    @ValueSource(strings = {"ValidPassword1!", "VALIDpassword1@", "Valid123$%^"})
    void shouldCreateRawPasswordWhenValidPasswordIsProvided(String password) {
        assertDoesNotThrow(() -> RawPassword.of(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void shouldThrowExceptionWhenPasswordIsInvalid(String password) {
        assertThrows(IllegalArgumentException.class, () -> RawPassword.of(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"InvalidPassword!", "INVALIDPASSWORD1!", "invalidpassword1!", "InvalidPassword1", "Inv1!"})
    void shouldThrowExceptionWhenPasswordDoesNotMeetRequirements(String password) {
        assertThrows(IllegalArgumentException.class, () -> RawPassword.of(password));
    }
}