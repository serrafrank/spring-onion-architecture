package org.pay_my_buddy.api_command.event_storage;

import java.time.LocalDateTime;
import org.pay_my_buddy.api_command.Event;
import org.pay_my_buddy.api_command.EventId;
import org.pay_my_buddy.shared.EntityId;

public interface EventWrapper {
	EventId eventId();

	LocalDateTime timestamp();

	int index();

	EntityId aggregateId();

	String aggregateType();

	String eventType();

	Event event();

}
