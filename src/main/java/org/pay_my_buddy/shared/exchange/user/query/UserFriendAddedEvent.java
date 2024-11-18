package org.pay_my_buddy.shared.exchange.user.query;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.pay_my_buddy.api_command.Event;
import org.pay_my_buddy.api_command.EventId;
import org.pay_my_buddy.shared.Constraint;
import org.pay_my_buddy.shared.exchange.user.UserId;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
public record UserFriendAddedEvent(EventId eventId, UserId userId, UserId friendId) implements Event {

	@JsonCreator
	public UserFriendAddedEvent {
		Constraint.checkIf(userId).isNotNull("User id is required");
		Constraint.checkIf(friendId).isNotNull("Friend id is required");
	}

	public UserFriendAddedEvent(UserId userId, UserId friendId) {
		this(new EventId(), userId, friendId);
	}

}
