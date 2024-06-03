package org.pay_my_buddy.entity.account;


import org.pay_my_buddy.entity.exception.generic.IllegalRequestException;

/**
 * InsufficientFundsException is an exception that is thrown when the account has insufficient funds to perform a transaction.
 * It extends the IllegalRequestException class.
 */
public class CurrencyMismatchException extends IllegalRequestException {

    public CurrencyMismatchException(String accountCurrency, String transactionCurrency) {
        super("Currency mismatch, cannot debit account with different currency. Account currency: " + accountCurrency + ", transaction currency: " + transactionCurrency);
    }
}
