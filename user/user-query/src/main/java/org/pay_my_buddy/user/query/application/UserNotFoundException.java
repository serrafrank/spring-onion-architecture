package org.pay_my_buddy.user.query.application;

import org.pay_my_buddy.shared.common.domain.exception.NotFoundException;
import org.pay_my_buddy.user.common.domain.UserId;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(UserId userId) {
        super("User not found with id = %s", userId);
    }
}
