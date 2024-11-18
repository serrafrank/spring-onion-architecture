package org.pay_my_buddy.api_command.event_storage;

import org.pay_my_buddy.shared.exception.NotFoundException;

public class AggregateNotFoundException extends NotFoundException {
	public AggregateNotFoundException(String message) {
		super(message);
	}
}
