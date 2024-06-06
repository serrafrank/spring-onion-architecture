package org.pay_my_buddy.entity.user.password_validator;

/**
 * This class represents the
 */
public class RawPasswordValidationRules {

    private RawPasswordValidationRules() {
        int maxAllowedLength = MIN_DIGITS + MIN_LOWERCASE + MIN_SPECIALS + MIN_UPPERCASES;

        if (maxAllowedLength > MAX_LENGTH) {
            throw new IllegalArgumentException("The sum of the minimum number of digits, lowercase, uppercase and special characters must be less than or equal to the maximum password length");
        }
    }

    public static final int MIN_LENGTH = 8;
    public static final int MAX_LENGTH = 30;
    public static final int MIN_UPPERCASES = 1;
    public static final int MIN_LOWERCASE = 1;
    public static final int MIN_DIGITS = 1;
    public static final int MIN_SPECIALS = 1;

    public static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    public static final String DIGIT_CHARACTERS = "0123456789";
    public static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+-=[]{}|;':,.<>?";
}
