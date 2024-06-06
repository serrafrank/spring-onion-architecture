package org.pay_my_buddy.application.use_case.account.command.create_account;

import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.exception.ConflictException;

public class AccountNotExistsException extends ConflictException {
    public AccountNotExistsException(Id id) {
        super("Account not exists for user " + id.value());
    }
}
