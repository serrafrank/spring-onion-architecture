package org.pay_my_buddy.core.framework.domain.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.pay_my_buddy.core.framework.domain.exception.BadArgumentException;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("StringValidatorTest tests")
class StringValidatorTest {

    @ParameterizedTest
    @DisplayName("GIVEN a non-null string WHEN isNotNull is called THEN it should succeed")
    @ValueSource(strings = {"test", " ", "123"})
    void givenNonNullString_whenIsNotNullCalled_thenShouldSucceed(String value) {
        StringValidator validator = new StringValidator(value);
        StringValidator result = validator.isNotNull();
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("GIVEN a null string WHEN isNotNull is called THEN it should throw an exception")
    @NullSource
    void givenNullString_whenIsNotNullCalled_thenShouldThrowException(String value) {
        StringValidator validator = new StringValidator(value);
        assertThatThrownBy(validator::isNotNull)
                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("GIVEN a non-blank string WHEN notBlank is called THEN it should succeed")
    @ValueSource(strings = {"test", "123", "a"})
    void givenNonBlankString_whenNotBlankCalled_thenShouldSucceed(String value) {
        StringValidator validator = new StringValidator(value);
        StringValidator result = validator.notBlank();
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("GIVEN a blank string WHEN notBlank is called THEN it should throw an exception")
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void givenBlankString_whenNotBlankCalled_thenShouldThrowException(String value) {
        StringValidator validator = new StringValidator(value);
        assertThatThrownBy(validator::notBlank)
                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("GIVEN a non-empty string WHEN notEmpty is called THEN it should succeed")
    @ValueSource(strings = {"test", " ", "123"})
    void givenNonEmptyString_whenNotEmptyCalled_thenShouldSucceed(String value) {
        StringValidator validator = new StringValidator(value);
        StringValidator result = validator.notEmpty();
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("GIVEN an empty string WHEN notEmpty is called THEN it should throw an exception")
    @ValueSource(strings = {""})
    void givenEmptyString_whenNotEmptyCalled_thenShouldThrowException(String value) {
        StringValidator validator = new StringValidator(value);
        assertThatThrownBy(validator::notEmpty)
                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("GIVEN a string of specific length WHEN length is called THEN it should succeed")
    @CsvSource({"test, 4", "12345, 5", "a, 1"})
    void givenStringOfSpecificLength_whenLengthCalled_thenShouldSucceed(String value, int length) {
        StringValidator validator = new StringValidator(value);
        StringValidator result = validator.length(length);
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("GIVEN a string of different length WHEN length is called THEN it should throw an exception")
    @CsvSource({"test, 3", "12345, 4", "a, 2"})
    void givenStringOfDifferentLength_whenLengthCalled_thenShouldThrowException(String value, int length) {
        StringValidator validator = new StringValidator(value);
        assertThatThrownBy(() -> validator.length(length))
                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("GIVEN a string matching regex WHEN matches is called THEN it should succeed")
    @CsvSource({"test@example.com, ^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", "12345, \\d+"})
    void givenStringMatchingRegex_whenMatchesCalled_thenShouldSucceed(String value, String regex) {
        StringValidator validator = new StringValidator(value);
        StringValidator result = validator.matches(regex);
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("GIVEN a string not matching regex WHEN matches is called THEN it should throw an exception")
    @CsvSource({"test@example, ^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", "abc, \\d+"})
    void givenStringNotMatchingRegex_whenMatchesCalled_thenShouldThrowException(String value, String regex) {
        StringValidator validator = new StringValidator(value);
        assertThatThrownBy(() -> validator.matches(regex))
                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("GIVEN a string containing sequence WHEN contains is called THEN it should succeed")
    @CsvSource({"hello world, world", "test, es", "12345, 234"})
    void givenStringContainingSequence_whenContainsCalled_thenShouldSucceed(String value, String sequence) {
        StringValidator validator = new StringValidator(value);
        StringValidator result = validator.contains(sequence);
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("GIVEN a string not containing sequence WHEN contains is called THEN it should throw an exception")
    @CsvSource({"hello world, abc", "test, xyz", "12345, 678"})
    void givenStringNotContainingSequence_whenContainsCalled_thenShouldThrowException(String value, String sequence) {
        StringValidator validator = new StringValidator(value);
        assertThatThrownBy(() -> validator.contains(sequence))
                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class);
    }
}