package org.pay_my_buddy.shared.domain.api;

@FunctionalInterface
public interface MessagePublisher {
    void publish(Message message);
}
