
package org.pay_my_buddy.entity.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.entity.account.exception.CurrencyMismatchException;
import org.pay_my_buddy.entity.account.exception.InsufficientFundsException;
import org.pay_my_buddy.entity.commun.entity.Id;
import org.pay_my_buddy.entity.commun.value_object.Amount;
import org.pay_my_buddy.entity.commun.value_object.CurrencyCode;
import org.pay_my_buddy.entity.user.UserId;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    @DisplayName("Can create an Account object with a non-empty Id and balance")
    void can_create_an_account_object_with_a_non_empty_id_and_balance() {
        // Given
        Id userId = UserId.of();
        Amount balance = Amount.of(100, CurrencyCode.USD);

        // When
        Account account = Account.of(userId, balance);

        // Then
        assertNotNull(account);
        assertEquals(userId, account.getUserId());
        assertEquals(balance, account.getBalance());
    }

    @Test
    @DisplayName("Can credit an Account object with a valid amount")
    void can_credit_an_account_object_with_a_valid_amount() {
        // Given
        Id userId = UserId.of();
        Amount balance = Amount.of(100, CurrencyCode.USD);
        Amount creditAmount = Amount.of(50, CurrencyCode.USD);
        Account account = Account.of(userId, balance);

        // When
        Account creditedAccount = account.credit(creditAmount);

        // Then
        assertEquals(Amount.of(150, CurrencyCode.USD), creditedAccount.getBalance());
    }

    @Test
    @DisplayName("Cannot credit an Account object with a different currency")
    void cannot_credit_an_account_object_with_a_different_currency() {
        // Given
        Id userId = UserId.of();
        Amount balance = Amount.of(100, CurrencyCode.USD);
        Amount creditAmount = Amount.of(50, CurrencyCode.EUR);
        Account account = Account.of(userId, balance);

        // When & Then
        assertThrows(CurrencyMismatchException.class, () -> account.credit(creditAmount));
    }

    @Test
    @DisplayName("Can debit an Account object with a valid amount")
    void can_debit_an_account_object_with_a_valid_amount() {
        // Given
        Id userId = UserId.of();
        Amount balance = Amount.of(100, CurrencyCode.USD);
        Amount debitAmount = Amount.of(50, CurrencyCode.USD);
        Account account = Account.of(userId, balance);

        // When
        Account debitedAccount = account.debit(debitAmount);

        // Then
        assertEquals(Amount.of(50, CurrencyCode.USD), debitedAccount.getBalance());
    }

    @Test
    @DisplayName("Cannot debit an Account object with a different currency")
    void cannot_debit_an_account_object_with_a_different_currency() {
        // Given
        Id userId = UserId.of();
        Amount balance = Amount.of(100, CurrencyCode.USD);
        Amount debitAmount = Amount.of(50, CurrencyCode.EUR);
        Account account = Account.of(userId, balance);

        // When & Then
        assertThrows(CurrencyMismatchException.class, () -> account.debit(debitAmount));
    }

    @Test
    @DisplayName("Cannot debit an Account object with an amount greater than the balance")
    void cannot_debit_an_account_object_with_an_amount_greater_than_the_balance() {
        // Given
        Id userId = UserId.of();
        Amount balance = Amount.of(100, CurrencyCode.USD);
        Amount debitAmount = Amount.of(150, CurrencyCode.USD);
        Account account = Account.of(userId, balance);

        // When & Then
        assertThrows(InsufficientFundsException.class, () -> account.debit(debitAmount));
    }
}