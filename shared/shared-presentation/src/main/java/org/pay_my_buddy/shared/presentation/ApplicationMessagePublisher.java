package org.pay_my_buddy.shared.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.shared.domain.api.Message;
import org.pay_my_buddy.shared.domain.api.MessagePublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationMessagePublisher implements MessagePublisher {

    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void publish(Message message) {
        log.debug("Publishing message {} - {}", message.getClass(), message);
        applicationEventPublisher.publishEvent(message);
    }
}