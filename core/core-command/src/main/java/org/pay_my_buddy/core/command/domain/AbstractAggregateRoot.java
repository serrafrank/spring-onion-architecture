package org.pay_my_buddy.core.command.domain;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.pay_my_buddy.core.command.domain.event_storage.AggregateEventListener;
import org.pay_my_buddy.core.command.domain.event_storage.EventWrapper;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;
import org.pay_my_buddy.core.framework.domain.message.Event;
import org.pay_my_buddy.core.framework.domain.message.EventId;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.core.framework.domain.exception.InternalErrorException;

public abstract class AbstractAggregateRoot<ID extends EntityId> implements Serializable {

    private static final List<EventWrapper> uncommitedChanges = new ArrayList<>();
    private static final List<EventWrapper> commitedChanges = new ArrayList<>();
    @Getter
    @Accessors(fluent = true)
    protected final ID id;

    protected AbstractAggregateRoot(ID id) {
        this.id = id;
    }


    public int version() {
        return uncommitedChanges.size() + commitedChanges.size();
    }

    public void commitChanges() {
        commitedChanges.addAll(uncommitedChanges);
        uncommitedChanges.clear();
    }

    public void rollbackChanges() {
        uncommitedChanges.clear();
        replayEvents();
    }

    public void addEvent(Event event) {
        var wrappedEvent = new AggregateEventWrapper(event, this.id, this.getClass().getName());
        applyChanges(event);
        uncommitedChanges.add(wrappedEvent);
    }

    public void recreateAggregate(List<EventWrapper> events) {
        events.sort(Comparator.comparing(EventWrapper::index));
        commitedChanges.addAll(events);
        rollbackChanges();
    }

    public List<EventWrapper> commitedChanges() {
        return commitedChanges;
    }

    public List<EventWrapper> uncommitedChanges() {
        return uncommitedChanges;
    }

    public void replayEvents() {
        commitedChanges
                .stream()
                .sorted(Comparator.comparing(EventWrapper::index))
                .forEach(e -> applyChanges(e.event()));
    }

    private <EVENT extends Event> void applyChanges(EVENT event) {
        Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AggregateEventListener.class))
                .filter(method -> method.getParameterTypes()[0].isAssignableFrom(event.getClass()))
                .reduce((a, b) -> {
                    throw BusinessException.wrap(new InternalErrorException("Multiple event handlers found for event type: " + event.getClass()));
                })
                .ifPresentOrElse(method -> invokeEventHandlerMethod(event, method), () -> {
                    throw BusinessException.wrap(new IllegalArgumentException("No handler registered for event type: " + event.getClass()));
                });
    }

    private <EVENT extends Event> void invokeEventHandlerMethod(EVENT event, Method method) {
        try {
            method.invoke(this, event);
        } catch (Exception e) {
            throw BusinessException.wrap(new IllegalArgumentException("Error applying event", e));
        }
    }

    protected record AggregateEventWrapper(
            EventId eventId,
            LocalDateTime timestamp,
            int index,
            EntityId aggregateId,
            String aggregateType,
            String eventType,
            Event event) implements EventWrapper {
        public AggregateEventWrapper(Event event, EntityId aggregateId, String aggregateType) {
            this(event.eventId(), LocalDateTime.now(Clock.systemUTC()), uncommitedChanges.size(),
                    aggregateId,
                    aggregateType,
                    event.getClass().getName(), event);
        }
    }
}

