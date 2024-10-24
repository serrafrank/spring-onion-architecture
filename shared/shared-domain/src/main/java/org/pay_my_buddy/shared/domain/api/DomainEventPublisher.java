package org.pay_my_buddy.shared.domain.api;

@FunctionalInterface
public interface DomainEventPublisher {
    void publish(DomainEvent command);
}
