package org.pay_my_buddy.modules.user.query.presentation;

import org.pay_my_buddy.core.framework.domain.exception.NotFoundException;
import org.pay_my_buddy.modules.user.shared.UserId;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(UserId userId) {
        super("User not found with userId = %s", userId);
    }
}
