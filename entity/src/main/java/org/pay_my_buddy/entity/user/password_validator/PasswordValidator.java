package org.pay_my_buddy.entity.user.password_validator;

public final class PasswordValidator {

    private PasswordValidator() {
    }


    public static PasswordValidatorResponse of(String password) {

        var response = new PasswordValidatorResponse();

        isBlank(password, response);
        lengthIsValid(password, response);
        uppercaseIsValid(password, response);
        lowercaseIsValid(password, response);
        digitIsValid(password, response);
        specialIsValid(password, response);

        return response;
    }


    private static void isBlank(String password, PasswordValidatorResponse response) {
        if (password == null || password.isBlank()) {
            response.addErrorMessage("Password cannot be blank");
        }
    }


    private static void lengthIsValid(String password, PasswordValidatorResponse response) {
        if (password.length() < RawPasswordValidationRules.MIN_LENGTH || password.length() > RawPasswordValidationRules.MAX_LENGTH) {
            response.addErrorMessage("Password must be between " + RawPasswordValidationRules.MIN_LENGTH + " and " + RawPasswordValidationRules.MAX_LENGTH + " characters");
        }
    }

    private static void uppercaseIsValid(String password, PasswordValidatorResponse response) {
        if (!match(password, RawPasswordValidationRules.UPPERCASE_CHARACTERS, RawPasswordValidationRules.MIN_UPPERCASES)) {
            response.addErrorMessage("Password must contain at least " + RawPasswordValidationRules.MIN_UPPERCASES + " uppercase character");
        }
    }

    private static void lowercaseIsValid(String password, PasswordValidatorResponse response) {
        if (!match(password, RawPasswordValidationRules.LOWERCASE_CHARACTERS, RawPasswordValidationRules.MIN_LOWERCASE)) {
            response.addErrorMessage("Password must contain at least " + RawPasswordValidationRules.MIN_LOWERCASE + " lowercase character");
        }
    }

    private static void digitIsValid(String password, PasswordValidatorResponse response) {
        if (!match(password, RawPasswordValidationRules.DIGIT_CHARACTERS, RawPasswordValidationRules.MIN_DIGITS)) {
            response.addErrorMessage("Password must contain at least " + RawPasswordValidationRules.MIN_DIGITS + " digit character");
        }
    }

    private static void specialIsValid(String password, PasswordValidatorResponse response) {
        if (!match(password, RawPasswordValidationRules.SPECIAL_CHARACTERS, RawPasswordValidationRules.MIN_SPECIALS)) {
            response.addErrorMessage("Password must contain at least " + RawPasswordValidationRules.MIN_SPECIALS + " special character");
        }
    }

    private static boolean match(String password, String pattern, int min) {
        return password.chars().filter(c -> pattern.indexOf(c) >= 0).count() >= min;
    }


}
