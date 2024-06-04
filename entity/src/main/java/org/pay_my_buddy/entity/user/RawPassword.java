package org.pay_my_buddy.entity.user;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.passay.*;
import org.pay_my_buddy.entity.AbstractValueObject;

import java.util.Arrays;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
public class RawPassword extends AbstractValueObject<String> {

    public RawPassword(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (!isValid(value)) {
            throw new IllegalArgumentException("Invalid format for password: " + value + ". \n" +
                    String.join("\n", messages(value)));
        }

        this.value = value;
    }

    public static RawPassword of(String password) {
        return new RawPassword(password);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    private boolean isValid(String password) {
        return this.result(password).isValid();
    }

    private RuleResult result(String password) {
        return this.validator().validate(new PasswordData(password));
    }

    private PasswordValidator validator() {
        return new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30), // length between 8 and 30 characters
//                new CharacterRule(EnglishCharacterData.LowerCase, 1), // at least one lower-case character
//                new CharacterRule(EnglishCharacterData.UpperCase, 1), // at least one upper-case character
//                new CharacterRule(EnglishCharacterData.Digit, 1), // at least one digit character
//                new CharacterRule(EnglishCharacterData.Special, 1), // at least one special character
                new WhitespaceRule())); // no whitespace
    }

    private List<String> messages(String password) {
        return this.validator().getMessages(this.result(password));
    }

}
