package org.pay_my_buddy.entity.application.account.exception;

import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.exception.generic.ConflictException;

public class AccountAlreadyExistsException extends ConflictException {
    public AccountAlreadyExistsException(Id id) {
        super("Account already exists for user " + id.value());
    }
}
