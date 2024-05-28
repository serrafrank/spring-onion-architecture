package org.pay_my_buddy.presentation.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.entity.common.api.event.Event;
import org.pay_my_buddy.entity.common.api.event.EventApi;
import org.pay_my_buddy.presentation.api.providers.EventHandlerProvider;
import org.springframework.stereotype.Component;

/**
 * This class is a Spring specific implementation of the EventApi interface.
 * It is annotated with @Component, meaning that Spring will automatically create an instance of this class and manage it.
 * It uses Lombok's @RequiredArgsConstructor to automatically generate a constructor that initializes final fields.
 * The final fields are automatically injected by Spring due to the @RequiredArgsConstructor annotation.
 * It is also annotated with @Slf4j, which provides a logger instance.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultEventApi implements EventApi {

    /**
     * The EventHandlerProvider instance. This is automatically injected by Spring.
     */
    private final EventHandlerProvider eventHandlerProvider;

    /**
     * This method is an implementation of the publish method defined in the EventApi interface.
     * It takes an event as input and publishes it to all the handlers that can handle this event.
     * It uses the EventHandlerProvider to get the appropriate handlers for the event.
     *
     * @param event the event to be published
     */
    @Override
    public <E extends Event> void publish(E event) {
        this.eventHandlerProvider.getHandlers(event).forEach(handler -> handler.handle(event));
    }
}