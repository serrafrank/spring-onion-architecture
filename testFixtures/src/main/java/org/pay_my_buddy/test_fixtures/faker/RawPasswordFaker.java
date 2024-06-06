package org.pay_my_buddy.test_fixtures.faker;

import net.datafaker.Faker;
import org.pay_my_buddy.entity.user.RawPassword;
import org.pay_my_buddy.entity.user.password_validator.RawPasswordValidationRules;
import org.pay_my_buddy.test_fixtures.helper.FakerHelper;


public record RawPasswordFaker(String rawPassword) {

    private static final Faker faker = new Faker();

        public RawPasswordFaker of(String rawPassword) {
            return new RawPasswordFaker(rawPassword);
        }

        public static RawPasswordFaker of() {
            final String characters = FakerHelper.stringify(RawPasswordValidationRules.UPPERCASE_CHARACTERS, RawPasswordValidationRules.MIN_UPPERCASES) +
                    FakerHelper.stringify(RawPasswordValidationRules.LOWERCASE_CHARACTERS, RawPasswordValidationRules.MIN_LOWERCASE) +
                    FakerHelper.stringify(RawPasswordValidationRules.DIGIT_CHARACTERS, RawPasswordValidationRules.MIN_DIGITS) +
                    FakerHelper.stringify(RawPasswordValidationRules.SPECIAL_CHARACTERS, RawPasswordValidationRules.MIN_SPECIALS) +
                    FakerHelper.alphabetic(RawPasswordValidationRules.MIN_DIGITS + RawPasswordValidationRules.MIN_LOWERCASE + RawPasswordValidationRules.MIN_SPECIALS + RawPasswordValidationRules.MIN_UPPERCASES, RawPasswordValidationRules.MIN_LENGTH);
            final int length = FakerHelper.number(RawPasswordValidationRules.MIN_LENGTH, RawPasswordValidationRules.MAX_LENGTH).intValue();

            final String password = FakerHelper.stringify(characters, length);

            return new RawPasswordFaker(password);

        }

        public RawPassword build() {
            return RawPassword.of(this.rawPassword);
        }
}
