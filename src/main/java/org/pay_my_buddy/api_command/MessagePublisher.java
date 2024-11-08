package org.pay_my_buddy.api_command;

import org.pay_my_buddy.shared.Message;

@FunctionalInterface
public interface MessagePublisher {
    void publish(Message message);
}
