package org.pay_my_buddy.entity.user;

import org.pay_my_buddy.entity.exception.generic.IllegalRequestException;

public class InvalidEmailException extends IllegalRequestException {
    public InvalidEmailException() {
        super("Invalid email");
    }

    public InvalidEmailException(String message) {
        super(message);
    }
}
