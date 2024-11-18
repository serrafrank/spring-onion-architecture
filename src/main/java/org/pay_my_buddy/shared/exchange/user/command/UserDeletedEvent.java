package org.pay_my_buddy.shared.exchange.user.command;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.pay_my_buddy.api_command.Event;
import org.pay_my_buddy.api_command.EventId;
import org.pay_my_buddy.shared.Constraint;
import org.pay_my_buddy.shared.exchange.user.UserId;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
public record UserDeletedEvent(EventId eventId, UserId userId) implements Event {


	@JsonCreator
	public UserDeletedEvent {
		Constraint.checkIf(userId).isNotNull("User id is required");
	}

	public UserDeletedEvent(UserId userId) {
		this(new EventId(), userId);
	}

}
