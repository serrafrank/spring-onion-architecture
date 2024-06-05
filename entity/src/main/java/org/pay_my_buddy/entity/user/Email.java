package org.pay_my_buddy.entity.user;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.apache.commons.validator.routines.EmailValidator;
import org.pay_my_buddy.entity.AbstractValueObject;

@Value
@EqualsAndHashCode(callSuper = true)
public class Email extends AbstractValueObject<String> {

    private Email(String value) {
        super(value);
        if (value.isBlank()) {
            throw new InvalidEmailException("Email cannot be null or empty");
        }
        if (!isValid(value)) {
            throw new InvalidEmailException("Invalid email format : " + value);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static Email of(String email) {
        return new Email(email);
    }

    public static Email of(Email email) {
        return new Email(email.value());
    }

    private boolean isValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
