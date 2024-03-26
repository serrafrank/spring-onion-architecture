package org.pay_my_buddy.presentation.api.providers;

import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.entity.commun.api.command.CommandHandler;
import org.pay_my_buddy.entity.commun.api.event.Event;
import org.pay_my_buddy.entity.commun.api.event.EventHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is a Spring specific implementation of a provider for EventHandler instances.
 * It is annotated with @Component, meaning that Spring will automatically create an instance of this class and manage it.
 * It is also annotated with @Slf4j, which provides a logger instance.
 * It maintains a map of Event classes to their respective sets of EventHandler instances.
 */
@Slf4j
@Component
public class EventHandlerProvider {

    /**
     * The map of Event classes to their respective sets of EventHandler instances.
     */
    private final Map<Class<? extends Event>, Set<EventHandler<? extends Event>>> eventHandlers;

    /**
     * Constructor for the EventHandlerProvider.
     * It takes a list of EventHandler instances, and maps each Event class to its respective set of handlers.
     *
     * @param handlers the list of EventHandler instances
     */
    public EventHandlerProvider(
    ApplicationContext applicationContext) {
        this.eventHandlers =  applicationContext.getBeansOfType(EventHandler.class)
                .values()
                .stream()
                .map(handler -> (EventHandler<?>) handler)
                .map(handler -> Map.entry(resolve(handler), handler))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toSet())));

        log.info("EventHandlerProvider initialized with {} handlers", this.eventHandlers.size());

    }

    /**
     * This method returns the set of EventHandler instances for a given Event.
     * If no handlers are found, it returns an empty set.
     *
     * @param event the event for which to get the handlers
     * @return the set of EventHandler instances for the given event, or an empty set if no handlers are found
     */
    @SuppressWarnings("unchecked")
    public <E extends Event> Set<EventHandler<E>> getHandlers(E event) {
        return eventHandlers.get(event.getClass())
                .stream()
                .map(handler -> (EventHandler<E>) handler)
                .collect(Collectors.toSet());
    }

    /**
     * This method resolves the Event class for a given EventHandler.
     * It uses the GenericTypeResolver from Spring to resolve the generic type argument of the EventHandler.
     *
     * @param handler the handler for which to resolve the Event class
     * @return the Event class for the given handler
     */
    @SuppressWarnings("unchecked")
    private <E extends Event> Class<E> resolve(EventHandler<E> handler) {
        return (Class<E>) GenericTypeResolver.resolveTypeArgument(handler.getClass(), EventHandler.class);
    }

}