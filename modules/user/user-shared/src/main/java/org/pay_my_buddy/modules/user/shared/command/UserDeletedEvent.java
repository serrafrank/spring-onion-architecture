package org.pay_my_buddy.modules.user.shared.command;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.pay_my_buddy.core.framework.domain.message.Event;
import org.pay_my_buddy.core.framework.domain.message.EventId;
import org.pay_my_buddy.core.framework.domain.validator.Validate;
import org.pay_my_buddy.modules.user.shared.UserId;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
public record UserDeletedEvent(EventId eventId, UserId userId) implements Event {


    @JsonCreator
    public UserDeletedEvent {
        Validate.checkIf(userId).isNotNull("User id is required");
    }

    public UserDeletedEvent(UserId userId) {
        this(new EventId(), userId);
    }

}
