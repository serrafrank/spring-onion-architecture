package com.example.paymybuddy.core.common.value_object;

import com.example.paymybuddy.application.shared.entity.CurrencyCode;
import com.example.paymybuddy.application.shared.value_object.Amount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class AmountTest {

    private Amount amount;

    @BeforeEach
    void setUp() {
        amount = new Amount(BigDecimal.valueOf(100), CurrencyCode.EUR);
    }

    @Test
    void shouldCreateAmountWithGivenParameters() {
        assertEquals(BigDecimal.valueOf(100), amount.getValue());
        assertEquals(Currency.getInstance(CurrencyCode.EUR.getCode()), amount.getCurrency());
    }

    @Test
    void shouldAddAmount() {
        Amount amountToAdd = new Amount(BigDecimal.valueOf(50), CurrencyCode.EUR);
        amount.add(amountToAdd);
        assertEquals(BigDecimal.valueOf(150), amount.getValue());
    }

    @Test
    void shouldSubtractAmount() {
        Amount amountToSubtract = new Amount(BigDecimal.valueOf(50), CurrencyCode.EUR);
        amount.subtract(amountToSubtract);
        assertEquals(BigDecimal.valueOf(50), amount.getValue());
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNegative() {
        final BigDecimal negativeAmount = BigDecimal.valueOf(-100);
        assertThrows(IllegalArgumentException.class, () -> new Amount(negativeAmount, CurrencyCode.EUR));
    }

    @Test
    void shouldCompareAmountsCorrectly() {
        Amount smallerAmount = new Amount(BigDecimal.valueOf(50), CurrencyCode.EUR);
        Amount largerAmount = new Amount(BigDecimal.valueOf(150), CurrencyCode.EUR);

        assertTrue(amount.isGreaterThan(smallerAmount));
        assertFalse(amount.isGreaterThan(largerAmount));
        assertTrue(amount.isLessThan(largerAmount));
        assertFalse(amount.isLessThan(smallerAmount));
        assertTrue(amount.isGreaterThanOrEqualTo(amount));
        assertTrue(amount.isLessThanOrEqualTo(amount));
    }

    @Test
    void shouldCheckZeroAmountCorrectly() {
        Amount zeroAmount = new Amount(BigDecimal.ZERO, CurrencyCode.EUR);
        assertTrue(zeroAmount.isZero());
        assertFalse(amount.isZero());
    }
}