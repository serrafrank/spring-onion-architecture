package org.pay_my_buddy.application.account;

import org.pay_my_buddy.entity.common.entity.Id;
import org.pay_my_buddy.entity.common.exception.generic.ConflictException;

public class AccountNotExistsException extends ConflictException {
    public AccountNotExistsException(Id id) {
        super("Account not exists for user " + id.value());
    }
}
