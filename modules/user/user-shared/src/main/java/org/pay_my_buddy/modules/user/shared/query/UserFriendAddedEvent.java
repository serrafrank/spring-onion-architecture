package org.pay_my_buddy.modules.user.shared.query;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.pay_my_buddy.core.framework.domain.message.Event;
import org.pay_my_buddy.core.framework.domain.message.EventId;
import org.pay_my_buddy.core.framework.domain.validator.Validate;
import org.pay_my_buddy.modules.user.shared.UserId;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
public record UserFriendAddedEvent(EventId eventId, UserId userId, UserId friendId) implements Event {

    @JsonCreator
    public UserFriendAddedEvent {
        Validate.checkIf(userId).isNotNull("User id is required");
        Validate.checkIf(friendId).isNotNull("Friend id is required");
    }

    public UserFriendAddedEvent(UserId userId, UserId friendId) {
        this(new EventId(), userId, friendId);
    }

}
