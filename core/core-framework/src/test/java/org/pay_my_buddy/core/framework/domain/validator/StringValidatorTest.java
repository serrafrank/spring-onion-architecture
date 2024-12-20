package org.pay_my_buddy.core.framework.domain.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.pay_my_buddy.core.framework.domain.exception.BadArgumentException;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("StringValidatorTest tests")
class StringValidatorTest {


    @ParameterizedTest
    @DisplayName("GIVEN a non-null string WHEN isNotNull is called THEN it should succeed")
    @MethodSource("provideNonNullStrings")
    void givenNonNullString_whenIsNotNullCalled_thenShouldSucceed(String value) {
        StringValidator validator = new StringValidator(value);
        StringValidator result = validator.isNotNull();
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("GIVEN a null string WHEN isNotNull is called THEN it should throw an exception")
    @MethodSource("provideNullStrings")
    void givenNullString_whenIsNotNullCalled_thenShouldThrowException(String value) {
        StringValidator validator = new StringValidator(value);
        assertThatThrownBy(validator::isNotNull)
                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("GIVEN a non-blank string WHEN notBlank is called THEN it should succeed")
    @MethodSource("provideNonBlankStrings")
    void givenNonBlankString_whenNotBlankCalled_thenShouldSucceed(String value) {
        StringValidator validator = new StringValidator(value);
        StringValidator result = validator.notBlank();
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("GIVEN a blank string WHEN notBlank is called THEN it should throw an exception")
    @MethodSource("provideBlankStrings")
    void givenBlankString_whenNotBlankCalled_thenShouldThrowException(String value) {
        StringValidator validator = new StringValidator(value);
        assertThatThrownBy(validator::notBlank)
                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("GIVEN a non-empty string WHEN notEmpty is called THEN it should succeed")
    @MethodSource("provideNonEmptyStrings")
    void givenNonEmptyString_whenNotEmptyCalled_thenShouldSucceed(String value) {
        StringValidator validator = new StringValidator(value);
        StringValidator result = validator.notEmpty();
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("GIVEN an empty string WHEN notEmpty is called THEN it should throw an exception")
    @MethodSource("provideEmptyStrings")
    void givenEmptyString_whenNotEmptyCalled_thenShouldThrowException(String value) {
        StringValidator validator = new StringValidator(value);
        assertThatThrownBy(validator::notEmpty)
                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("GIVEN a string of specific length WHEN length is called THEN it should succeed")
    @MethodSource("provideStringsOfSpecificLength")
    void givenStringOfSpecificLength_whenLengthCalled_thenShouldSucceed(String value, int length) {
        StringValidator validator = new StringValidator(value);
        StringValidator result = validator.length(length);
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("GIVEN a string of different length WHEN length is called THEN it should throw an exception")
    @MethodSource("provideStringsOfDifferentLength")
    void givenStringOfDifferentLength_whenLengthCalled_thenShouldThrowException(String value, int length) {
        StringValidator validator = new StringValidator(value);
        assertThatThrownBy(() -> validator.length(length))
                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("GIVEN a string matching regex WHEN matches is called THEN it should succeed")
    @MethodSource("provideStringsMatchingRegex")
    void givenStringMatchingRegex_whenMatchesCalled_thenShouldSucceed(String value, String regex) {
        StringValidator validator = new StringValidator(value);
        StringValidator result = validator.matches(regex);
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("GIVEN a string not matching regex WHEN matches is called THEN it should throw an exception")
    @MethodSource("provideStringsNotMatchingRegex")
    void givenStringNotMatchingRegex_whenMatchesCalled_thenShouldThrowException(String value, String regex) {
        StringValidator validator = new StringValidator(value);
        assertThatThrownBy(() -> validator.matches(regex))
                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("GIVEN a string containing sequence WHEN contains is called THEN it should succeed")
    @MethodSource("provideStringsContainingSequence")
    void givenStringContainingSequence_whenContainsCalled_thenShouldSucceed(String value, String sequence) {
        StringValidator validator = new StringValidator(value);
        StringValidator result = validator.contains(sequence);
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("GIVEN a string not containing sequence WHEN contains is called THEN it should throw an exception")
    @MethodSource("provideStringsNotContainingSequence")
    void givenStringNotContainingSequence_whenContainsCalled_thenShouldThrowException(String value, String sequence) {
        StringValidator validator = new StringValidator(value);
        assertThatThrownBy(() -> validator.contains(sequence))
                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class);
    }


    private static Stream<String> provideNonNullStrings() {
        return Stream.of("test", " ", "123");
    }

    private static Stream<String> provideNullStrings() {
        return Stream.of((String) null);
    }

    private static Stream<String> provideNonBlankStrings() {
        return Stream.of("test", "123", "a");
    }

    private static Stream<String> provideBlankStrings() {
        return Stream.of("", " ", "\t", "\n");
    }

    private static Stream<String> provideNonEmptyStrings() {
        return Stream.of("test", " ", "123");
    }

    private static Stream<String> provideEmptyStrings() {
        return Stream.of("");
    }

    private static Stream<Arguments> provideStringsOfSpecificLength() {
        return Stream.of(
                Arguments.of("test", 4),
                Arguments.of("12345", 5),
                Arguments.of("a", 1)
        );
    }

    private static Stream<Arguments> provideStringsOfDifferentLength() {
        return Stream.of(
                Arguments.of("test", 3),
                Arguments.of("12345", 4),
                Arguments.of("a", 2)
        );
    }

    private static Stream<Arguments> provideStringsMatchingRegex() {
        return Stream.of(
                Arguments.of("test@example.com", "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"),
                Arguments.of("12345", "\\d+")
        );
    }

    private static Stream<Arguments> provideStringsNotMatchingRegex() {
        return Stream.of(
                Arguments.of("test@example", "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"),
                Arguments.of("abc", "\\d+")
        );
    }

    private static Stream<Arguments> provideStringsContainingSequence() {
        return Stream.of(
                Arguments.of("hello world", "world"),
                Arguments.of("test", "es"),
                Arguments.of("12345", "234")
        );
    }

    private static Stream<Arguments> provideStringsNotContainingSequence() {
        return Stream.of(
                Arguments.of("hello world", "abc"),
                Arguments.of("test", "xyz"),
                Arguments.of("12345", "678")
        );
    }
}