package org.pay_my_buddy.modules.user.shared.query;

import org.pay_my_buddy.core.framework.domain.message.Event;
import org.pay_my_buddy.core.framework.domain.message.EventId;
import org.pay_my_buddy.core.framework.domain.validator.Validate;
import org.pay_my_buddy.modules.user.shared.UserId;


public record UserFriendRemovedEvent(EventId eventId, UserId userId, UserId friendId) implements Event {


    public UserFriendRemovedEvent {
        Validate.checkIf(userId).isNotNull("User id is required");
        Validate.checkIf(friendId).isNotNull("Friend id is required");
    }

    public UserFriendRemovedEvent(UserId userId, UserId friendId) {
        this(new EventId(), userId, friendId);
    }

}
