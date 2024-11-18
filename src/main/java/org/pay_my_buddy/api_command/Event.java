package org.pay_my_buddy.api_command;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.pay_my_buddy.shared.Message;

@JsonSerialize
public interface Event extends Message {
	EventId eventId();
}
