package org.pay_my_buddy.api_command;

import org.pay_my_buddy.shared.Message;

public interface Event extends Message {
	int version();
	void version(int version);
	void incrementVersion();
}
