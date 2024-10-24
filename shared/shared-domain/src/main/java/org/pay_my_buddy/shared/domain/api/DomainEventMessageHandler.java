package org.pay_my_buddy.shared.domain.api;

@FunctionalInterface
public interface DomainEventMessageHandler<DOMAIN_EVENT extends DomainEvent> extends MessageHandler<DOMAIN_EVENT> {

}