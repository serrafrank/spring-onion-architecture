package org.pay_my_buddy.user_query.presentation;

import org.pay_my_buddy.shared.exception.NotFoundException;
import org.pay_my_buddy.shared.exchange.user.UserId;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(UserId userId) {
        super("User not found with userId = %s", userId);
    }
}
