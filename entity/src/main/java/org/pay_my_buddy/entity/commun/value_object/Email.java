package org.pay_my_buddy.entity.commun.value_object;

import org.apache.commons.validator.routines.EmailValidator;
import org.pay_my_buddy.entity.commun.exception.InvalidEmailException;

public record Email(String value) {

    private static final EmailValidator emailValidator = EmailValidator.getInstance();

    public Email {
        if (value == null || value.isBlank()) {
            throw new InvalidEmailException("Email cannot be null or empty");
        }
        if (!emailValidator.isValid(value)) {
            throw new InvalidEmailException("Invalid email format : " + value);
        }
    }

    public static Email of(String email) {
        return new Email(email);
    }

    @Override
    public String toString() {
        return value;
    }
}
