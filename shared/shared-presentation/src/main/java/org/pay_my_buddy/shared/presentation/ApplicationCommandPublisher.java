package org.pay_my_buddy.shared.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.shared.domain.api.Command;
import org.pay_my_buddy.shared.domain.api.CommandPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationCommandPublisher implements CommandPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void publish(Command command) {
        applicationEventPublisher.publishEvent(command);
    }
}
