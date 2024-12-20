package org.pay_my_buddy.core.framework.domain;

import org.pay_my_buddy.core.framework.domain.message.Message;

@FunctionalInterface
public interface MessagePublisher {
    void publish(Message message);
}
