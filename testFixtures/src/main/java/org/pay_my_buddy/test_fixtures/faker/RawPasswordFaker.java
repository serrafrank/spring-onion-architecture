package org.pay_my_buddy.test_fixtures.faker;

import net.datafaker.Faker;
import org.pay_my_buddy.entity.user.RawPassword;
import org.pay_my_buddy.entity.user.password_validator.RawPasswordValidationRules;


public record RawPasswordFaker(String rawPassword) {

    private static final Faker faker = new Faker();

    public RawPasswordFaker of(String rawPassword) {
        return new RawPasswordFaker(rawPassword);
    }

    public static RawPasswordFaker of() {
        final String characters = FakerTool.stringify(RawPasswordValidationRules.UPPERCASE_CHARACTERS, RawPasswordValidationRules.MIN_UPPERCASES) +
                FakerTool.stringify(RawPasswordValidationRules.LOWERCASE_CHARACTERS, RawPasswordValidationRules.MIN_LOWERCASE) +
                FakerTool.stringify(RawPasswordValidationRules.DIGIT_CHARACTERS, RawPasswordValidationRules.MIN_DIGITS) +
                FakerTool.stringify(RawPasswordValidationRules.SPECIAL_CHARACTERS, RawPasswordValidationRules.MIN_SPECIALS) +
                FakerTool.alphabetic(RawPasswordValidationRules.MIN_DIGITS + RawPasswordValidationRules.MIN_LOWERCASE + RawPasswordValidationRules.MIN_SPECIALS + RawPasswordValidationRules.MIN_UPPERCASES, RawPasswordValidationRules.MIN_LENGTH);
        final int length = FakerTool.number(RawPasswordValidationRules.MIN_LENGTH, RawPasswordValidationRules.MAX_LENGTH).intValue();

        final String password = FakerTool.stringify(characters, length);

        return new RawPasswordFaker(password);

    }

    public RawPassword build() {
        return RawPassword.of(this.rawPassword);
    }
}
