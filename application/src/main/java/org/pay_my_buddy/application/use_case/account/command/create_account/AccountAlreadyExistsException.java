package org.pay_my_buddy.application.use_case.account.command.create_account;

import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.exception.ConflictException;

public class AccountAlreadyExistsException extends ConflictException {
    public AccountAlreadyExistsException(Id id) {
        super("Account already exists for user " + id.value());
    }
}
