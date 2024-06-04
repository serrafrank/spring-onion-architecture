package org.pay_my_buddy.application.common.value_object;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.entity.user.EncodedPassword;

import static org.junit.jupiter.api.Assertions.*;

class EncodedPasswordTest {

    @Test
    @DisplayName("Can create an EncodedPassword object with a non-empty string value")
    void can_create_an_encoded_password_object_with_a_non_empty_string_value() {
        // Given
        String encodedPassword = "password123";

        // When
        EncodedPassword password = EncodedPassword.of(encodedPassword);

        // Then
        assertNotNull(password);
        assertEquals(encodedPassword, password.value());
    }

    @Test
    @DisplayName("Cannot create an EncodedPassword object with a null value")
    void cannot_create_an_encoded_password_object_with_a_null_value() {
        // Given
        String encodedPassword = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> EncodedPassword.of(encodedPassword));
    }

    @Test
    @DisplayName("Cannot create an EncodedPassword object with an empty string value")
    void cannot_create_an_encoded_password_object_with_an_empty_string_value() {
        // Given
        String emptyString = "";

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> EncodedPassword.of(emptyString));
        assertEquals("Password cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Two EncodedPassword objects with the same string value are equal")
    void two_encoded_password_objects_with_the_same_string_value_are_equal() {
        // Given
        String password = "password";
        EncodedPassword encodedPassword1 = EncodedPassword.of(password);
        EncodedPassword encodedPassword2 = EncodedPassword.of(password);

        // When & Then
        assertEquals(encodedPassword1, encodedPassword2);
    }

    @Test
    @DisplayName("Two EncodedPassword objects with different string values are not equal")
    void two_encoded_password_objects_with_different_string_values_are_not_equal() {
        // Given
        String password1 = "password1";
        String password2 = "password2";

        // When
        EncodedPassword encodedPassword1 = EncodedPassword.of(password1);
        EncodedPassword encodedPassword2 = EncodedPassword.of(password2);

        // Then
        assertNotEquals(encodedPassword1, encodedPassword2);
    }

    @Test
    @DisplayName("EncodedPassword is not equal to objects of other classes")
    void encoded_password_is_not_equal_to_objects_of_other_classes() {
        // Given
        EncodedPassword encodedPassword = EncodedPassword.of("password");
        Object otherObject = new Object();

        // When
        boolean result = encodedPassword.equals(otherObject);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("EncodedPassword objects can be compared to null")
    void encoded_password_objects_can_be_compared_to_null() {
        // Given
        EncodedPassword encodedPassword = EncodedPassword.of("password");

        // When
        boolean result = encodedPassword.equals(null);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("EncodedPassword objects can be compared to other types of objects")
    void encoded_password_objects_can_be_compared_to_other_types_of_objects() {
        // Given
        EncodedPassword password = EncodedPassword.of("password");
        String otherObject = "password";

        // When
        boolean result = password.equals(otherObject);

        // Then
        assertFalse(result);
    }
}