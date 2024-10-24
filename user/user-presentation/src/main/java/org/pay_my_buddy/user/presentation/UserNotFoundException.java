package org.pay_my_buddy.user.presentation;

import org.pay_my_buddy.shared.domain.exception.NotFoundException;
import org.pay_my_buddy.user.domain.UserId;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(UserId userId) {
        super("User not found with id = %s", userId);
    }
}
