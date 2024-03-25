package org.pay_my_buddy.application.user;

import org.pay_my_buddy.entity.commun.exception.generic.ConflictException;
import org.pay_my_buddy.entity.commun.value_object.Email;

public class EmailAlreadyExistsException extends ConflictException {
    public EmailAlreadyExistsException(Email email) {
        super("Email already exists: %s", email);
    }
}
