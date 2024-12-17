package org.pay_my_buddy.modules.user.shared.command;

import org.pay_my_buddy.core.framework.domain.message.Event;
import org.pay_my_buddy.core.framework.domain.message.EventId;
import org.pay_my_buddy.core.framework.domain.validator.Validate;
import org.pay_my_buddy.modules.user.shared.UserId;


public record UserDeletedEvent(EventId eventId, UserId userId) implements Event {


    public UserDeletedEvent {
        Validate.checkIf(userId).isNotNull("User id is required");
    }

    public UserDeletedEvent(UserId userId) {
        this(new EventId(), userId);
    }

}
