package org.pay_my_buddy.application.faker;

import net.datafaker.Faker;
import org.pay_my_buddy.entity.user.password_validator.RawPasswordValidationRules;

public final class FakerTool {
    private FakerTool() {
    }

    public static Faker data() {
        return new Faker();
    }

    public static String email() {
        return data().internet().emailAddress();
    }

    public static String password() {
        return FakerTool.stringify(RawPasswordValidationRules.UPPERCASE_CHARACTERS, RawPasswordValidationRules.MIN_UPPERCASES) +
                FakerTool.stringify(RawPasswordValidationRules.LOWERCASE_CHARACTERS, RawPasswordValidationRules.MIN_LOWERCASE) +
                FakerTool.stringify(RawPasswordValidationRules.DIGIT_CHARACTERS, RawPasswordValidationRules.MIN_DIGITS) +
                FakerTool.stringify(RawPasswordValidationRules.SPECIAL_CHARACTERS, RawPasswordValidationRules.MIN_SPECIALS) +
                FakerTool.alphanumeric(RawPasswordValidationRules.MIN_LENGTH);
    }

    public static String alphanumeric() {
        return alphanumeric(10);
    }

    public static String alphanumeric(int length) {
        return data().regexify("[a-zA-Z0-9]{" + length + "}");
    }

    public static String alphabetic() {
        return alphabetic(10);
    }

    public static String alphabetic(int length) {
        return data().regexify("[a-zA-Z]{" + length + "}");
    }

    public static String alphabetic(int minLength, int maxLength) {
        return alphabetic(number(minLength, maxLength).intValue());
    }


    public static String numeric() {
        return numeric(10);
    }

    public static String numeric(int length) {
        return data().regexify("[0-9]{" + length + "}");
    }

    public static String numeric(int minLength, int maxLength) {
        return numeric(number(minLength, maxLength).intValue());
    }

    public static String special() {
        return special(10);
    }

    public static String special(int length) {
        return data().regexify("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]{" + length + "}");
    }

    public static String special(int minLength, int maxLength) {
        return special(number(minLength, maxLength).intValue());
    }


    public static Number number(long min, long max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be less than or equal to max");
        }
        return data().number().numberBetween(min, max);
    }

    public static Number number() {
        return number(1, 10);
    }

    public static String regexify(String regex) {
        return data().regexify(regex);
    }

    public static String stringify(String string) {
        return stringify(string, 10);
    }

    public static String stringify(String string, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(string.charAt(number(0, string.length()).intValue()));
        }
        return stringBuilder.toString();
    }

    public static String stringify(String string, int minLength, int maxLength) {
        return stringify(string, number(minLength, maxLength).intValue());
    }

}
