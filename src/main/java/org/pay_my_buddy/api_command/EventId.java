package org.pay_my_buddy.api_command;

import org.pay_my_buddy.shared.Constraint;
import org.pay_my_buddy.shared.EntityId;

public record EventId(String value) implements EntityId {

	private final static String PREFIX = "EVENT_";

	public EventId {
		Constraint.checkIf(value).isNotNull("EventId is required")
				.predicate(v -> EntityId.isValid(v, PREFIX), "EventId not valid");
	}

	public EventId() {
		this(PREFIX + EntityId.generateId());
	}

	public EventId(EntityId value) {
		this(value.value());
	}
}
