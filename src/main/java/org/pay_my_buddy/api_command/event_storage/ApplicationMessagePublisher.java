package org.pay_my_buddy.api_command.event_storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.api_command.MessagePublisher;
import org.pay_my_buddy.shared.Message;
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
