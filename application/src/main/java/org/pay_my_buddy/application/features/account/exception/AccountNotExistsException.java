package org.pay_my_buddy.application.features.account.exception;

import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.exception.generic.ConflictException;

public class AccountNotExistsException extends ConflictException {
    public AccountNotExistsException(Id id) {
        super("Account not exists for user " + id.value());
    }
}
