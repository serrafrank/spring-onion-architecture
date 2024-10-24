package org.pay_my_buddy.shared.domain.api;

@FunctionalInterface
public interface MessageHandler<MESSAGE extends Message> {
    void handle(MESSAGE message);
}
