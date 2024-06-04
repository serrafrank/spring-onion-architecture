package org.pay_my_buddy.application.common.api.event;

/**
 * The EventApi interface is the entry point for all events in the application.
 * It provides a method to dispatch an event.
 */
public interface EventApi {

    <E extends Event> void publish(E event);
}
