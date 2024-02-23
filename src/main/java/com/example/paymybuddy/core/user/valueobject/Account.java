package com.example.paymybuddy.core.user.valueobject;

import com.example.paymybuddy.core.common.valueobject.Amount;
import com.example.paymybuddy.core.validator.Constraint;
import lombok.Value;

import java.io.Serializable;

/**
 * Account is a class that represents a user's account in the system.
 * It implements the Serializable interface.
 * It contains information about the account such as balance and currency.
 * It also provides methods to credit, debit and check the balance of the account.
 *
 * @author serrafrank
 * @version 1.0
 * @since 2023.3.3
 */
@Value
public class Account implements Serializable {

    /**
     * The balance of the account.
     */
    Amount balance = new Amount();

    /**
     * Credits the account with a specified amount.
     *
     * @param amount The amount to be credited to the account.
     */
    public void credit(Amount amount) {
        this.balance.add(amount);
    }

    /**
     * Debits the account with a specified amount.
     * Throws an IllegalArgumentException if the balance is less than the amount to be debited.
     *
     * @param amount The amount to be debited from the account.
     * @throws IllegalArgumentException If the balance is less than the amount to be debited.
     */
    public void debit(Amount amount) {
        if (!this.balance.getCurrency().equals(amount.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch, cannot debit account with different currency");
        }

        if (this.balance.isLessThan(amount)) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        this.balance.subtract(amount);
    }
}