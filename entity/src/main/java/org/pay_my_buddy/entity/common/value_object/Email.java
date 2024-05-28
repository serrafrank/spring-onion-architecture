package org.pay_my_buddy.entity.common.value_object;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;
import org.apache.commons.validator.routines.EmailValidator;
import org.pay_my_buddy.entity.common.exception.InvalidEmailException;

@Value
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class Email extends AbstractValueObject<String> {

    private static final EmailValidator emailValidator = EmailValidator.getInstance();


    public Email(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidEmailException("Email cannot be null or empty");
        }
        if (!emailValidator.isValid(value)) {
            throw new InvalidEmailException("Invalid email format : " + value);
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static Email of(String email) {
        return new Email(email);
    }

}
