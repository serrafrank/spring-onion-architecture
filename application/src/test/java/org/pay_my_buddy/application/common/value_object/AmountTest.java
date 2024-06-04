package org.pay_my_buddy.application.common.value_object;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.pay_my_buddy.entity.account.CurrencyMismatchException;
import org.pay_my_buddy.entity.amount.Amount;
import org.pay_my_buddy.entity.amount.CurrencyCode;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AmountTest {

    private static Stream<Arguments> provideValidAmountsAndCurrencies() {
        return Stream.of(
                Arguments.of(0, CurrencyCode.EUR),
                Arguments.of(0.01, CurrencyCode.EUR),
                Arguments.of(100, CurrencyCode.EUR),
                Arguments.of(100.01, CurrencyCode.EUR),
                Arguments.of(999999999999999999999999999.99, CurrencyCode.EUR),
                Arguments.of(0, CurrencyCode.USD),
                Arguments.of(0.01, CurrencyCode.USD),
                Arguments.of(100, CurrencyCode.USD),
                Arguments.of(100.01, CurrencyCode.USD),
                Arguments.of(999999999999999999999999999.99, CurrencyCode.USD)
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidAmountsAndCurrencies")
    @DisplayName("Creating an Amount object with a positive BigDecimal value and a valid CurrencyCode should not throw an exception")
    void create_amount_with_positive_bigDecimal_and_valid_currency(Number value, CurrencyCode currencyCode) {
        // Given a positive BigDecimal value and a valid CurrencyCode
        BigDecimal bigDecimalValue = new BigDecimal(String.valueOf(value));

        // When creating an Amount object
        Amount amount = Amount.of(bigDecimalValue, currencyCode);

        // Then the Amount object should not be null and should have the correct value and Currency
        assertThat(amount).isNotNull();
        assertThat(amount.value()).isEqualByComparingTo(bigDecimalValue);
        assertThat(amount.currency().getCurrencyCode()).isEqualTo(currencyCode.code());
    }


    @ParameterizedTest
    @MethodSource("provideValidAmountsAndCurrencies")
    @DisplayName("Creating an Amount object with a positive Number value and a valid CurrencyCode should not throw an exception")
    void create_amount_with_positive_number_and_valid_currency(Number value, CurrencyCode currencyCode) {
        // Given a positive BigDecimal value and a valid CurrencyCode

        // When creating an Amount object
        Amount amount = Amount.of(value, currencyCode);

        // Then the Amount object should not be null and should have the correct value and Currency
        assertThat(amount).isNotNull();
        assertThat(amount.value()).isEqualByComparingTo(new BigDecimal(String.valueOf(value)));
        assertThat(amount.currency().getCurrencyCode()).isEqualTo(currencyCode.code());
    }

    @Test
    @DisplayName("adding an Amount object with the same Currency as this object should return a new Amount object with the correct value and Currency")
    void add_amount_with_same_currency() {
        // Given two Amount objects with the same Currency and different values
        Amount amount1 = Amount.of(new BigDecimal("100"), CurrencyCode.EUR);
        Amount amount2 = Amount.of(new BigDecimal("50"), CurrencyCode.EUR);

        // When adding the two Amount objects
        Amount result = amount1.add(amount2);

        // Then the result should not be null and should have the correct value and Currency
        assertThat(result).isNotNull();
        assertThat(result.value()).isEqualByComparingTo(new BigDecimal("150"));
        assertThat(result.currency().getCurrencyCode()).isEqualTo(CurrencyCode.EUR.code());
    }

    @Test
    @DisplayName("subtracting an Amount object with the same Currency as this object should return a new Amount object with the correct value and Currency")
    void subtract_amount_with_same_currency() {
        // Given two Amount objects with the same Currency and different values
        Amount amount1 = Amount.of(new BigDecimal("100"), CurrencyCode.EUR);
        Amount amount2 = Amount.of(new BigDecimal("50"), CurrencyCode.EUR);

        // When subtracting the two Amount objects
        Amount result = amount1.subtract(amount2);

        // Then the result should not be null and should have the correct value and Currency
        assertThat(result).isNotNull();
        assertThat(result.value()).isEqualByComparingTo(new BigDecimal("50"));
        assertThat(result.currency().getCurrencyCode()).isEqualTo(CurrencyCode.EUR.code());
    }

    @Test
    @DisplayName("checking if this Amount object is less than another Amount object with the same Currency should return the correct boolean value")
    void is_less_than_with_same_currency() {
        // Given two Amount objects with the same Currency and different values
        Amount amount1 = Amount.of(new BigDecimal("100"), CurrencyCode.EUR);
        Amount amount2 = Amount.of(new BigDecimal("200"), CurrencyCode.EUR);

        // When checking if the first Amount object is less than the second Amount object
        boolean result1 = amount1.isLessThan(amount2);
        boolean result2 = amount2.isLessThan(amount1);

        // Then the result should be true for the first Amount object and false for the second Amount object
        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    @DisplayName("creating an Amount object with the default values should return an Amount object with a value of zero and the default Currency")
    void create_amount_with_default_values() {
        // Given no parameters

        // When creating an Amount object
        Amount amount = Amount.of();

        // Then the Amount object should not be null and should have a value of zero and the default Currency
        assertThat(amount).isNotNull();
        assertThat(amount.value()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(amount.currency().getCurrencyCode()).isEqualTo(CurrencyCode.EUR.code());
    }

    @Test
    @DisplayName("creating an Amount object with a negative BigDecimal value should throw an IllegalArgumentException")
    void create_amount_with_negative_value() {
        // Given a negative BigDecimal value and a valid CurrencyCode
        BigDecimal value = new BigDecimal("-100");
        CurrencyCode currencyCode = CurrencyCode.EUR;

        // When creating an Amount object
        ThrowableAssert.ThrowingCallable throwingCallable = () -> Amount.of(value, currencyCode);

        // When creating an Amount object, Then an IllegalArgumentException should be thrown with the correct message
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount cannot be negative");
    }

    @Test
    @DisplayName("adding an Amount object with a different Currency than this object should throw a CurrencyMismatchException")
    void add_amount_with_different_currency() {
        // Given two Amount objects with different Currencies
        Amount amount1 = Amount.of(new BigDecimal("100"), CurrencyCode.EUR);
        Amount amount2 = Amount.of(new BigDecimal("50"), CurrencyCode.USD);

        // When adding the two Amount objects
        ThrowableAssert.ThrowingCallable throwingCallable = () -> amount1.add(amount2);

        // Then a CurrencyMismatchException should be thrown with the correct message
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(CurrencyMismatchException.class)
                .hasMessage("Currency mismatch, cannot debit account with different currency. Account currency: EUR, transaction currency: USD");
    }

    @Test
    @DisplayName("subtracting an Amount object with a different Currency than this object should throw a CurrencyMismatchException")
    void subtract_amount_with_different_currency() {
        // Given two Amount objects with different Currencies
        Amount amount1 = Amount.of(new BigDecimal("100"), CurrencyCode.EUR);
        Amount amount2 = Amount.of(new BigDecimal("50"), CurrencyCode.USD);

        // When subtracting the two Amount objects
        ThrowableAssert.ThrowingCallable throwingCallable = () -> amount1.subtract(amount2);

        // Then a CurrencyMismatchException should be thrown with the correct message
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(CurrencyMismatchException.class)
                .hasMessage("Currency mismatch, cannot debit account with different currency. Account currency: EUR, transaction currency: USD");
    }

    @Test
    @DisplayName("checking if this Amount object is less than another Amount object with a different Currency should throw a CurrencyMismatchException")
    void is_less_than_with_different_currency() {
        // Given two Amount objects with different Currencies
        Amount amount1 = Amount.of(new BigDecimal("100"), CurrencyCode.EUR);
        Amount amount2 = Amount.of(new BigDecimal("200"), CurrencyCode.USD);

        // When checking if the first Amount object is less than the second Amount object
        ThrowableAssert.ThrowingCallable throwingCallable1 = () -> amount1.isLessThan(amount2);
        ThrowableAssert.ThrowingCallable throwingCallable2 = () -> amount2.isLessThan(amount1);

        // Then a CurrencyMismatchException should be thrown with the correct message
        assertThatThrownBy(throwingCallable1)
                .isInstanceOf(CurrencyMismatchException.class)
                .hasMessage("Currency mismatch, cannot debit account with different currency. Account currency: EUR, transaction currency: USD");

        assertThatThrownBy(throwingCallable2)
                .isInstanceOf(CurrencyMismatchException.class)
                .hasMessage("Currency mismatch, cannot debit account with different currency. Account currency: USD, transaction currency: EUR");
    }

    @Test
    @DisplayName("creating an Amount object with the maximum BigDecimal value should not throw an exception")
    void create_amount_with_maximum_value() {
        // Given the maximum BigDecimal value and a valid CurrencyCode
        BigDecimal value = new BigDecimal(Double.MAX_VALUE);
        CurrencyCode currencyCode = CurrencyCode.EUR;

        // When creating an Amount object
        Amount amount = Amount.of(value, currencyCode);

        // Then the Amount object should not be null
        assertThat(amount).isNotNull();
        assertThat(amount.value()).isEqualByComparingTo(value);
        assertThat(amount.currency().getCurrencyCode()).isEqualTo(currencyCode.code());
    }

    @Test
    @DisplayName("creating an Amount object with the minimum BigDecimal value should not throw an exception")
    void create_amount_with_minimum_value() {
        // Given the minimum BigDecimal value and a valid CurrencyCode
        BigDecimal minValue = BigDecimal.valueOf(Double.MIN_VALUE);

        // When creating an Amount object
        Amount amount = Amount.of(minValue, CurrencyCode.EUR);

        // Then the Amount object should not be null
        assertThat(amount).isNotNull();
        assertThat(amount.value()).isEqualByComparingTo(minValue);
        assertThat(amount.currency().getCurrencyCode()).isEqualTo(CurrencyCode.EUR.code());
    }

    @Test
    @DisplayName("adding an Amount object with the maximum BigDecimal value should not throw an exception")
    void adding_amount_with_maximum_value_should_not_throw_exception() {
        // Given the maximum BigDecimal value
        Amount amount1 = Amount.of(new BigDecimal("999999999999999999999999999.99"), CurrencyCode.USD);
        Amount amount2 = Amount.of(new BigDecimal("0.01"), CurrencyCode.USD);

        // When adding the two Amount objects
        Amount result = amount1.add(amount2);

        // Then the result should not be null and should have the correct value and Currency
        assertThat(result).isNotNull();
        assertThat(result.value()).isEqualByComparingTo(new BigDecimal("1000000000000000000000000000"));
        assertThat(result.currency().getCurrencyCode()).isEqualTo(CurrencyCode.USD.code());
    }

    @Test
    @DisplayName("checking if this Amount object is less than another Amount object with the same value should return false")
    void is_less_than_with_same_value_should_return_false() {
        // Given two Amount objects with the same Currency and value
        Amount amount1 = Amount.of(BigDecimal.TEN, CurrencyCode.EUR);
        Amount amount2 = Amount.of(BigDecimal.TEN, CurrencyCode.EUR);

        // When checking if the first Amount object is less than the second Amount object
        boolean result = amount1.isLessThan(amount2);

        // Then
        assertThat(result).isFalse();
    }

}