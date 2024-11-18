package org.pay_my_buddy.shared.exchange.user;

import org.pay_my_buddy.shared.Constraint;
import org.pay_my_buddy.shared.EntityId;

public record UserId(String value) implements EntityId {

	private final static String PREFIX = "USER_";

	public UserId {
		Constraint.checkIf(value).isNotNull("EventId is required")
				.predicate(v -> EntityId.isValid(v, PREFIX), "EventId not valid");
	}

	public UserId() {
		this(PREFIX + EntityId.generateId());
	}

	public UserId(EntityId value) {
		this(value.value());
	}
}
