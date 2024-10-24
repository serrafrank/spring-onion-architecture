package org.pay_my_buddy.shared.domain.api;

@FunctionalInterface
public interface CommandPublisher {
    <COMMAND extends Command> void publish(COMMAND command);
}
