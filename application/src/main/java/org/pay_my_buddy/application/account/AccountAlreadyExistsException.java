package org.pay_my_buddy.application.account;

import org.pay_my_buddy.entity.commun.entity.Id;
import org.pay_my_buddy.entity.exception.ConflictException;

public class AccountAlreadyExistsException extends ConflictException {
    public AccountAlreadyExistsException(Id id) {
        super("Account already exists for user " + id.value());
    }
}
