package com.example.paymybuddy.application.account.domain;

import com.example.paymybuddy.core.common.entity.aggregate.AbstractAggregateEntity;
import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.common.value_object.Amount;
import lombok.EqualsAndHashCode;
import lombok.Value;

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
@EqualsAndHashCode(callSuper = true)
@Value
public class AccountAggregate extends AbstractAggregateEntity<AccountId> {

    /**
     * The user id associated with the account.
     */
    private Id userId;

    /**
     * The balance of the account.
     */
    private final Amount balance = new Amount();

    /**
     * Credits the account with a specified amount.
     *
     * @param amount The amount to be credited to the account.
     */
    public AccountAggregate credit(Amount amount) {
        this.balance.add(amount);
        return this;
    }

    /**
     * Debits the account with a specified amount.
     * Throws an IllegalArgumentException if the balance is less than the amount to be debited.
     *
     * @param amount The amount to be debited from the account.
     * @throws IllegalArgumentException If the balance is less than the amount to be debited.
     */
    public AccountAggregate debit(Amount amount) {
        if (!this.balance.getCurrency().equals(amount.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch, cannot debit account with different currency");
        }

        if (this.balance.isLessThan(amount)) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        this.balance.subtract(amount);
        return this;
    }
}