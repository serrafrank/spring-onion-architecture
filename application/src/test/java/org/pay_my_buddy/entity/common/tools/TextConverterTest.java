package org.pay_my_buddy.entity.common.tools;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextConverterTest {

    private static Stream<Arguments> provideTextToConvert() {
        return Stream.of(
                Arguments.of("hello world"),
                Arguments.of("Hello World"),
                Arguments.of("hello_world"),
                Arguments.of("HELLO_WORLD"),
                Arguments.of("HelloWorld"),
                Arguments.of("hello-world")
        );
    }

    @ParameterizedTest
    @MethodSource("provideTextToConvert")
    void toSnakeCase(String input) {
        assertEquals("hello_world", TextConverter.toSnakeCase(input));
    }

    @ParameterizedTest
    @MethodSource("provideTextToConvert")
    void toScreamingSnakeCase(String input) {
        assertEquals("HELLO_WORLD", TextConverter.toScreamingSnakeCase(input));
    }

    @ParameterizedTest
    @MethodSource("provideTextToConvert")
    void toKebabCase(String input) {
        assertEquals("hello-world", TextConverter.toKebabCase(input));
    }

    @ParameterizedTest
    @MethodSource("provideTextToConvert")
    void toCamelCase(String input) {
        assertEquals("helloWorld", TextConverter.toCamelCase(input));
    }

    @ParameterizedTest
    @MethodSource("provideTextToConvert")
    void toPascalCase(String input) {
        assertEquals("HelloWorld", TextConverter.toPascalCase(input));
    }

    @ParameterizedTest
    @MethodSource("provideTextToConvert")
    void toUpperCase(String input) {
        assertEquals("HELLOWORLD", TextConverter.toUpperCase(input));
    }

    @ParameterizedTest
    @MethodSource("provideTextToConvert")
    void toSentenceCase(String input) {
        assertEquals("Hello World", TextConverter.toSentenceCase(input));
    }
}