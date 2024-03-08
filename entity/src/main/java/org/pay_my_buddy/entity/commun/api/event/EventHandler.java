package org.pay_my_buddy.entity.commun.api.event;

import org.pay_my_buddy.entity.commun.api.Handler;

/**
 * The EventHandler interface is the entry point for all events in the application.
 * It provides a method to execute an event.
 * Each specific event handler will implement this interface.
 *
 * @param <E> The type of the event, which extends Event.
 */
public interface EventHandler<E extends Event> extends Handler<E> {

    void handle(E event);

}