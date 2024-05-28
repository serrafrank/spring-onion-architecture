package org.pay_my_buddy.entity.common.value_object;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.pay_my_buddy.entity.common.exception.InvalidEmailException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @Test
    @DisplayName("creating an Email object with a null value should throw an InvalidEmailException")
    void create_email_with_null_value() {
        // Given a null value
        String emailValue = null;

        // When creating an Email object
        ThrowableAssert.ThrowingCallable throwingCallable = () -> Email.of(emailValue);

        // Then an InvalidEmailException should be thrown with the correct message
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email cannot be null or empty");
    }

    @Test
    @DisplayName("creating an Email object with an empty value should throw an InvalidEmailException")
    void create_email_with_empty_value() {
        // Given an empty value
        String emailValue = "";

        // When creating an Email object
        ThrowableAssert.ThrowingCallable throwingCallable = () -> Email.of(emailValue);

        // Then an InvalidEmailException should be thrown with the correct message
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email cannot be null or empty");
    }

    @ParameterizedTest
    @DisplayName("creating an Email object with an invalid format should throw an InvalidEmailException")
    @ValueSource(strings = {"email", "email@", "@domain.com", "email@domain", "email@domain."})
    void create_email_with_invalid_format(String emailValue) {
        // Given an invalid email format

        // When creating an Email object
        ThrowableAssert.ThrowingCallable throwingCallable = () -> Email.of(emailValue);

        // Then an InvalidEmailException should be thrown with the correct message
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Invalid email format : " + emailValue);
    }

    @Test
    @DisplayName("creating an Email object with a valid format should not throw an exception")
    void create_email_with_valid_format() {
        // Given a valid email format
        String emailValue = "email@domain.com";

        // When creating an Email object
        Email email = Email.of(emailValue);

        // Then the Email object should not be null and should have the correct value
        assertThat(email).isNotNull();
        assertThat(email.value()).isEqualTo(emailValue);
    }
}