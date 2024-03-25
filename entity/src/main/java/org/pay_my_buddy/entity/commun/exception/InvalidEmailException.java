package org.pay_my_buddy.entity.commun.exception;

import org.pay_my_buddy.entity.commun.exception.generic.IllegalRequestException;

public class InvalidEmailException extends IllegalRequestException {
    public InvalidEmailException() {
        super("Invalid email");
    }

    public InvalidEmailException(String message) {
        super(message);
    }
}
