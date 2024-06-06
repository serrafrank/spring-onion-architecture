package org.pay_my_buddy.entity.user;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.pay_my_buddy.entity.AbstractValueObject;
import org.pay_my_buddy.entity.user.password_validator.PasswordValidator;
import org.pay_my_buddy.entity.user.password_validator.PasswordValidatorResponse;

@Value
@EqualsAndHashCode(callSuper = true)
public class RawPassword extends AbstractValueObject<String> {

    public RawPassword(String value) {
        super(value);
        if (value.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        final PasswordValidatorResponse validatorResponse = PasswordValidator.of(value);

        if (!validatorResponse.isValid()) {
            throw new IllegalArgumentException("Invalid format for password: " + value + ". \n" +
                    String.join("\n", validatorResponse.messages()));
        }
    }

    public static RawPassword of(String password) {
        return new RawPassword(password);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
