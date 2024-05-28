package org.pay_my_buddy.entity.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.pay_my_buddy.entity.account.exception.CurrencyMismatchException;
import org.pay_my_buddy.entity.account.exception.InsufficientFundsException;
import org.pay_my_buddy.entity.common.entity.AbstractModel;
import org.pay_my_buddy.entity.common.entity.Id;
import org.pay_my_buddy.entity.common.value_object.Amount;

/**
 * Account is a class that represents a user's account in the system.
 * It contains information about the account such as balance and currency.
 * It also provides methods to credit, debit and check the balance of the account.
 */
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Account extends AbstractModel<AccountId> {

    /**
     * The user id associated with the account.
     */
    private final Id userId;

    /**
     * The balance of the account.
     */
    private Amount balance;


    private Account(AccountId id, Id userId, Amount balance) {
        super(id);
        this.userId = userId;
        this.balance = balance;
    }

    public static Account of(Id userId) {
        return new Account(AccountId.of(), userId, Amount.of());
    }


    public static Account of(Id userId, Amount balance) {
        return new Account(AccountId.of(), userId, balance);
    }

    public static Account of(AccountId id, Id userId, Amount balance) {
        return new Account(id, userId, balance);
    }

    /**
     * Credits the account with a specified amount.
     * Throws a CurrencyMismatchException if the currency of the balance is different from the currency of the account.
     *
     * @param amount The amount to be credited to the account.
     * @throws CurrencyMismatchException If the currency of the balance is different from the currency of the account.
     */
    public Account credit(Amount amount) {
        if (!this.balance.currency().equals(amount.currency())) {
            throw new CurrencyMismatchException(balance.currency().getCurrencyCode(), amount.currency().getCurrencyCode());
        }

        this.balance = this.balance.add(amount);
        return this;
    }

    /**
     * Debits the account with a specified amount.
     * Throws a CurrencyMismatchException if the currency of the balance is different from the currency of the account.
     * Throws an InsufficientFundsException if the balance is less than the amount to be debited.
     *
     * @param amount The amount to be debited from the account.
     * @throws InsufficientFundsException If the balance is less than the amount to be debited.
     */
    public Account debit(Amount amount) {
        if (!this.balance.currency().equals(amount.currency())) {
            throw new CurrencyMismatchException(balance.currency().getCurrencyCode(), amount.currency().getCurrencyCode());
        }

        if (this.balance.isLessThan(amount)) {
            throw new InsufficientFundsException();
        }
        this.balance = this.balance.subtract(amount);
        return this;
    }
}