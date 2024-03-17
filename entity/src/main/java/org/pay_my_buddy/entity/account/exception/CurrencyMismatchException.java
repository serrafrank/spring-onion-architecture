package org.pay_my_buddy.entity.account.exception;

import org.pay_my_buddy.entity.exception.BadRequestException;

/**
 * InsufficientFundsException is an exception that is thrown when the account has insufficient funds to perform a transaction.
 * It extends the BadRequestException class.
 */
public class CurrencyMismatchException extends BadRequestException {

        public CurrencyMismatchException(String accountCurrency, String transactionCurrency) {
            super("Currency mismatch, cannot debit account with different currency. Account currency: " + accountCurrency + ", transaction currency: " + transactionCurrency);
        }
}
