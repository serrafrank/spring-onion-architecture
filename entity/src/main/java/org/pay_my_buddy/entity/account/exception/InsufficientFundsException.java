package org.pay_my_buddy.entity.account.exception;

import org.pay_my_buddy.entity.exception.IllegalRequestException;

/**
 * InsufficientFundsException is an exception that is thrown when the account has insufficient funds to perform a transaction.
 * It extends the IllegalRequestException class.
 */
public class InsufficientFundsException extends IllegalRequestException {

    public InsufficientFundsException() {
        super("Insufficient funds");
    }
}
