package org.pay_my_buddy.core.framework.domain.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.pay_my_buddy.core.framework.domain.exception.BadArgumentException;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("NumericValidator tests")
class NumericValidatorTest {

    @ParameterizedTest
    @DisplayName("GIVEN a positive number WHEN isPositive is called THEN it should succeed")
    @MethodSource("providePositiveNumbers")
    void testIsPositiveSuccess(Number value) {
        // GIVEN a positive number
        NumericValidator validator = new NumericValidator(value);

        // WHEN isPositive is called
        NumericValidator result = validator.isPositive();

        // THEN it should succeed
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("GIVEN a non-positive number WHEN isPositive is called THEN it should throw an exception")
    @MethodSource("provideNonPositiveNumbers")
    void testIsPositiveFailure(Number value) {
        // GIVEN a non-positive number
        NumericValidator validator = new NumericValidator(value);

        // WHEN/THEN it should throw an exception
        assertThatThrownBy(validator::isPositive)
                .isInstanceOf(BusinessException.class)
                    .hasCauseInstanceOf(BadArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("GIVEN a negative number WHEN isNegative is called THEN it should succeed")
    @MethodSource("provideNegativeNumbers")
    void testIsNegativeSuccess(Number value) {
        // GIVEN a negative number
        NumericValidator validator = new NumericValidator(value);

        // WHEN isNegative is called
        NumericValidator result = validator.isNegative();

        // THEN it should succeed
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("GIVEN a non-negative number WHEN isNegative is called THEN it should throw an exception")
    @MethodSource("provideNonNegativeNumbers")
    void testIsNegativeFailure(Number value) {
        // GIVEN a non-negative number
        NumericValidator validator = new NumericValidator(value);

        // WHEN/THEN it should throw an exception
        assertThatThrownBy(validator::isNegative)
                .isInstanceOf(BusinessException.class)
                    .hasCauseInstanceOf(BadArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("GIVEN a number WHEN isZero is called THEN it validates correctly")
    @MethodSource("provideNumbersForIsZero")
    void testIsZero(Number value, boolean isValid) {
        // GIVEN a number
        NumericValidator validator = new NumericValidator(value);

        // WHEN isZero is called
        if (isValid) {
            NumericValidator result = validator.isZero();
            // THEN it should succeed
            assertThat(result).isNotNull();
        } else {
            // THEN it should throw an exception
            assertThatThrownBy(validator::isZero)
                    .isInstanceOf(BusinessException.class)
                    .hasCauseInstanceOf(BadArgumentException.class);
        }
    }

    @ParameterizedTest
    @DisplayName("GIVEN a number WHEN isGreaterThan is called THEN it validates correctly")
    @MethodSource("provideNumbersForIsGreaterThan")
    void testIsGreaterThan(Number value, Number comparison, boolean isValid) {
        // GIVEN a number
        NumericValidator validator = new NumericValidator(value);

        // WHEN isGreaterThan is called
        if (isValid) {
            NumericValidator result = validator.isGreaterThan(comparison);
            // THEN it should succeed
            assertThat(result).isNotNull();
        } else {
            // THEN it should throw an exception
            assertThatThrownBy(() -> validator.isGreaterThan(comparison))
                    .isInstanceOf(BusinessException.class)
                    .hasCauseInstanceOf(BadArgumentException.class);
        }
    }

    @ParameterizedTest
    @DisplayName("GIVEN a number WHEN isLessThan is called THEN it validates correctly")
    @MethodSource("provideNumbersForIsLessThan")
    void testIsLessThan(Number value, Number comparison, boolean isValid) {
        // GIVEN a number
        NumericValidator validator = new NumericValidator(value);

        // WHEN isLessThan is called
        if (isValid) {
            NumericValidator result = validator.isLessThan(comparison);
            // THEN it should succeed
            assertThat(result).isNotNull();
        } else {
            // THEN it should throw an exception
            assertThatThrownBy(() -> validator.isLessThan(comparison))
                    .isInstanceOf(BusinessException.class)
                    .hasCauseInstanceOf(BadArgumentException.class);
        }
    }

    @ParameterizedTest
    @DisplayName("GIVEN a number WHEN isBetween is called THEN it validates correctly")
    @MethodSource("provideNumbersForIsBetween")
    void testIsBetween(Number value, Number min, Number max, boolean isValid) {
        // GIVEN a number
        NumericValidator validator = new NumericValidator(value);

        // WHEN isBetween is called
        if (isValid) {
            NumericValidator result = validator.isBetween(min, max);
            // THEN it should succeed
            assertThat(result).isNotNull();
        } else {
            // THEN it should throw an exception
            assertThatThrownBy(() -> validator.isBetween(min, max))
                    .isInstanceOf(BusinessException.class)
                    .hasCauseInstanceOf(BadArgumentException.class);
        }
    }

    // --- Data providers for @MethodSource ---

    static Stream<Number> providePositiveNumbers() {
        return Stream.of(1, 0.1, 100);
    }

    static Stream<Number> provideNonPositiveNumbers() {
        return Stream.of(0, -1, -100.5);
    }

    static Stream<Number> provideNegativeNumbers() {
        return Stream.of(-1, -0.1, -100);
    }

    static Stream<Number> provideNonNegativeNumbers() {
        return Stream.of(0, 1, 100.5);
    }

    static Stream<Object[]> provideNumbersForIsZero() {
        return Stream.of(
                new Object[]{0, true},
                new Object[]{1, false},
                new Object[]{-1, false}
        );
    }

    static Stream<Object[]> provideNumbersForIsGreaterThan() {
        return Stream.of(
                new Object[]{10, 5, true},
                new Object[]{10, 10, false},
                new Object[]{5, 10, false}
        );
    }

    static Stream<Object[]> provideNumbersForIsLessThan() {
        return Stream.of(
                new Object[]{5, 10, true},
                new Object[]{10, 10, false},
                new Object[]{15, 10, false}
        );
    }

    static Stream<Object[]> provideNumbersForIsBetween() {
        return Stream.of(
                new Object[]{5, 0, 10, true},
                new Object[]{10, 0, 10, true},
                new Object[]{0, 0, 10, true},
                new Object[]{15, 0, 10, false},
                new Object[]{-1, 0, 10, false}
        );
    }
}
