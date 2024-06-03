package org.pay_my_buddy.entity.application.user;

import org.pay_my_buddy.entity.exception.generic.ConflictException;
import org.pay_my_buddy.entity.user.Email;

public class EmailAlreadyExistsException extends ConflictException {
    public EmailAlreadyExistsException(Email email) {
        super("Email already exists: %s", email);
    }
}
