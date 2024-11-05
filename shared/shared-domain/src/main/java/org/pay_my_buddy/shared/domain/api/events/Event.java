package org.pay_my_buddy.shared.domain.api.events;

import org.pay_my_buddy.shared.domain.api.Message;

public interface Event extends Message {
	int version();
	void version(int version);
	void incrementVersion();
}
