package com.example.paymybuddy.core.common.value_object;

import com.example.paymybuddy.core.common.entity.CurrencyCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * This class represents an amount of money.
 * It uses BigDecimal to accurately represent the value of the amount.
 * It provides methods to perform operations on the amount such as add, subtract, and compare.
 * It also provides methods to check the sign of the amount and to get the value of the amount.
 */
@Getter
@NoArgsConstructor
public class Amount implements Serializable {

    private static final CurrencyCode DEFAULT_CURRENCY = CurrencyCode.EUR;

    // The value of the amount.
    private BigDecimal value = BigDecimal.ZERO;

    // The currency
    private Currency currency = Currency.getInstance(DEFAULT_CURRENCY.getCode());


    /**
     * Constructor that accepts a BigDecimal value for the amount and a CurrencyCode.
     *
     * @param value    The BigDecimal value of the amount.
     * @param currency The CurrencyCode of the amount.
     */
    public Amount(BigDecimal value, CurrencyCode currency) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        this.value = value;
        this.currency = Currency.getInstance(currency.getCode());
    }

    /**
     * Method to add an amount to this amount.
     *
     * @param amount The amount to add.
     */
    public void add(Amount amount) {
        this.value = this.value.add(amount.getValue());
    }

    /**
     * Method to subtract an amount from this amount.
     *
     * @param amount The amount to subtract.
     */
    public void subtract(Amount amount) {
        this.value = this.value.subtract(amount.getValue());
    }

    /**
     * Method to check if this amount is greater than another amount.
     *
     * @param amount The amount to compare with.
     * @return True if this amount is greater, false otherwise.
     */
    public boolean isGreaterThan(Amount amount) {
        return this.value.compareTo(amount.getValue()) > 0;
    }

    /**
     * Method to check if this amount is less than another amount.
     *
     * @param amount The amount to compare with.
     * @return True if this amount is less, false otherwise.
     */
    public boolean isLessThan(Amount amount) {
        return this.value.compareTo(amount.getValue()) < 0;
    }

    /**
     * Method to check if this amount is greater than or equal to another amount.
     *
     * @param amount The amount to compare with.
     * @return True if this amount is greater than or equal, false otherwise.
     */
    public boolean isGreaterThanOrEqualTo(Amount amount) {
        return this.value.compareTo(amount.getValue()) >= 0;
    }

    /**
     * Method to check if this amount is less than or equal to another amount.
     *
     * @param amount The amount to compare with.
     * @return True if this amount is less than or equal, false otherwise.
     */
    public boolean isLessThanOrEqualTo(Amount amount) {
        return this.value.compareTo(amount.getValue()) <= 0;
    }

    /**
     * Method to check if this amount is zero.
     *
     * @return True if this amount is zero, false otherwise.
     */
    public boolean isZero() {
        return this.value.compareTo(BigDecimal.ZERO) == 0;
    }


}