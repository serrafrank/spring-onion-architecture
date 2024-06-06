package org.pay_my_buddy.application.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TextConverter {
    private static final Pattern SNAKE_CASE_PATTERN = Pattern.compile("^[a-z]+(_[a-z]+)*$");
    private static final Pattern SCREAMING_SNAKE_CASE_PATTERN = Pattern.compile("^[A-Z]+(_[A-Z]+)*$");
    private static final Pattern KEBAB_CASE_PATTERN = Pattern.compile("^[a-z]+(-[a-z]+)*$");
    private static final Pattern CAMEL_CASE_PATTERN = Pattern.compile("^[a-z]+([A-Z][a-z]*)*$");
    private static final Pattern PASCAL_CASE_PATTERN = Pattern.compile("^[A-Z][a-z]*([A-Z][a-z]*)*$");


    public static String toSnakeCase(String text) {
        return String.join("_", splitText(text));
    }

    public static String toScreamingSnakeCase(String text) {
        return String.join("_", splitText(text)).toUpperCase();
    }

    public static String toKebabCase(String text) {
        return String.join("-", splitText(text));
    }

    public static String toCamelCase(String text) {
        String result = toPascalCase(text);
        return result.substring(0, 1).toLowerCase() + result.substring(1);
    }

    public static String toPascalCase(String text) {
        String[] words = splitText(text);
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
        }
        return result.toString();
    }

    public static String toUpperCase(String text) {
        return String.join("", splitText(text)).toUpperCase();
    }

    public static String toSentenceCase(String text) {
        String[] words = splitText(text);
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        return result.toString().trim();
    }

    private static String[] splitText(String text) {
        if (SNAKE_CASE_PATTERN.matcher(text).matches() || SCREAMING_SNAKE_CASE_PATTERN.matcher(text).matches()) {
            return text.toLowerCase().split("_");
        } else if (KEBAB_CASE_PATTERN.matcher(text).matches()) {
            return text.toLowerCase().split("-");
        } else if (CAMEL_CASE_PATTERN.matcher(text).matches() || PASCAL_CASE_PATTERN.matcher(text).matches()) {
            return Arrays.stream(text.split("(?=[A-Z])")).map(String::toLowerCase).toArray(String[]::new);
        } else {
            return text.toLowerCase().split(" ");
        }
    }
}