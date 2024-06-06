package org.pay_my_buddy.application.use_case.user.command.create_user;

import org.pay_my_buddy.entity.exception.ConflictException;
import org.pay_my_buddy.entity.user.Email;

public class EmailAlreadyExistsException extends ConflictException {
    public EmailAlreadyExistsException(Email email) {
        super("Email already exists: %s", email);
    }
}
