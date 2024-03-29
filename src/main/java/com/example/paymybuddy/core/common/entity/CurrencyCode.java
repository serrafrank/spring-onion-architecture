package com.example.paymybuddy.core.common.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * CurrencyCode is an enum that represents the different types of currencies.
 * It contains the currency codes for Euro (EUR), British Pound (GBP), Japanese Yen (JPY), and US Dollar (USD).
 * Each currency code is represented as a string.
 * It also overrides the toString method to return the currency code.
 *
 * @author serrafrank
 * @version 1.0
 * @since 2023.3.3
 */
@Getter
@RequiredArgsConstructor
public enum CurrencyCode {
    /**
     * The currency code for Euro.
     */
    EUR("EUR"),

    /**
     * The currency code for British Pound.
     */
    GBP("GBP"),

    /**
     * The currency code for Japanese Yen.
     */
    JPY("JPY"),

    /**
     * The currency code for US Dollar.
     */
    USD("USD");

    /**
     * The code of the currency.
     */
    private final String code;

    /**
     * Returns the code of the currency.
     *
     * @return The code of the currency.
     */
    @Override
    public String toString() {
        return code;
    }
}