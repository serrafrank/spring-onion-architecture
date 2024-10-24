package org.pay_my_buddy.shared.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.shared.domain.api.DomainEvent;
import org.pay_my_buddy.shared.domain.api.DomainEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void publish(DomainEvent domainEvent) {
        applicationEventPublisher.publishEvent(domainEvent);
    }
}
