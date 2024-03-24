package org.pay_my_buddy.entity.account;

import lombok.Getter;
import org.pay_my_buddy.entity.account.exception.CurrencyMismatchException;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * This class represents an amount of money.
 * It uses BigDecimal to accurately represent the value of the amount.
 * It provides methods to perform operations on the amount such as add, subtract, and compare.
 * It also provides methods to check the sign of the amount and to get the value of the amount.
 */
@Getter
public class Amount {

    private static final CurrencyCode DEFAULT_CURRENCY = CurrencyCode.EUR;

    // The value of the amount.
    private final BigDecimal value;

    // The currency
    private final Currency currency;


    /**
     * Private constructor that accepts a BigDecimal value for the amount and a CurrencyCode.
     *
     * @param value    The BigDecimal value of the amount.
     * @param currency The Currency of the amount.
     */
    private Amount(BigDecimal value, Currency currency) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        this.value = value;
        this.currency = currency;
    }

    /**
     * Constructor that accepts a BigDecimal value for the amount and a CurrencyCode.
     *
     * @param value    The BigDecimal value of the amount.
     * @param currency The CurrencyCode of the amount.
     */
    private Amount(BigDecimal value, CurrencyCode currency) {
        this(value, Currency.getInstance(currency.getCode()));
    }

    /**
     * Default constructor that initializes the amount to zero and the currency to the default currency.
     * The default currency is EUR.
     * The default amount is zero.
     */
    public static Amount of() {
        return new Amount(BigDecimal.ZERO, DEFAULT_CURRENCY);
    }

    /**
     * Static factory method to create an amount with a specified value and currency.
     *
     * @param amount       The value of the amount.
     * @param currencyCode The currency of the amount.
     */
    public static Amount of(BigDecimal amount, CurrencyCode currencyCode) {
        return new Amount(amount, Currency.getInstance(currencyCode.getCode()));
    }

    /**
     * Method to add an amount to this amount.
     *
     * @param amount The amount to add.
     */
    public Amount add(Amount amount) {
        this.validateCurrency(amount);
        return new Amount(this.value.add(amount.getValue()), this.currency);
    }

    /**
     * Method to subtract an amount from this amount.
     *
     * @param amount The amount to subtract.
     */
    public Amount subtract(Amount amount) {
        this.validateCurrency(amount);
        return new Amount(this.value.subtract(amount.getValue()), this.currency);
    }


    /**
     * Method to check if this amount is less than another amount.
     *
     * @param amount The amount to compare with.
     * @return True if this amount is less, false otherwise.
     */
    public boolean isLessThan(Amount amount) {
        this.validateCurrency(amount);
        return this.value.compareTo(amount.getValue()) < 0;
    }

    /**
     * Method to check if this amount uses the same currency as another amount.
     * If the currency is not the same, a CurrencyMismatchException is thrown.
     *
     * @param amount The amount to compare with.
     */
    private void validateCurrency(Amount amount) {
        if (!this.currency.equals(amount.getCurrency())) {
            throw new CurrencyMismatchException(this.currency.getCurrencyCode(), amount.getCurrency().getCurrencyCode());
        }
    }

}