package org.pay_my_buddy.user.infrastructure.listener;

import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.shared.domain.api.DomainEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GlobalDomainEventListener {
    @EventListener
    public void eventHandler(DomainEvent message) {
        log.info("GlobalDomainEventListener: {}", message);
    }
}
