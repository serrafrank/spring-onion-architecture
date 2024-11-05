package org.pay_my_buddy.shared.common.domain.api;

public interface Event extends Message {
	int version();
	void version(int version);
	void incrementVersion();
}
