package org.pay_my_buddy.core.command.domain.event_storage;

import org.pay_my_buddy.core.framework.domain.message.Message;

@FunctionalInterface
public interface MessagePublisher {
    void publish(Message message);
}
