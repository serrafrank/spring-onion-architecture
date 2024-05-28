package org.pay_my_buddy.application.account;

import org.pay_my_buddy.entity.common.entity.Id;
import org.pay_my_buddy.entity.common.exception.generic.ConflictException;

public class AccountAlreadyExistsException extends ConflictException {
    public AccountAlreadyExistsException(Id id) {
        super("Account already exists for user " + id.value());
    }
}
