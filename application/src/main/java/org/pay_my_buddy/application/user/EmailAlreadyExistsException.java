package org.pay_my_buddy.application.user;

import org.pay_my_buddy.entity.common.exception.generic.ConflictException;
import org.pay_my_buddy.entity.common.value_object.Email;

public class EmailAlreadyExistsException extends ConflictException {
    public EmailAlreadyExistsException(Email email) {
        super("Email already exists: %s", email);
    }
}
