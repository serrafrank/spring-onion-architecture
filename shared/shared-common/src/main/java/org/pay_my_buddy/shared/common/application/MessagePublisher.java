package org.pay_my_buddy.shared.common.application;

import org.pay_my_buddy.shared.common.domain.api.Message;

@FunctionalInterface
public interface MessagePublisher {
    void publish(Message message);
}
