package org.pay_my_buddy.application.user;

import org.pay_my_buddy.entity.exception.ConflictException;

public class EmailAlreadyExistsException extends ConflictException {
    public EmailAlreadyExistsException(String email) {
        super("Email already exists: %s", email);
    }
}
