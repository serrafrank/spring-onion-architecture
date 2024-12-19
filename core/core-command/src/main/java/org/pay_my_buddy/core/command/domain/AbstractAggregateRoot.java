package org.pay_my_buddy.core.command.domain;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.pay_my_buddy.core.command.domain.event_storage.AggregateEventListener;
import org.pay_my_buddy.core.command.domain.event_storage.EventWrapper;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.core.framework.domain.exception.InternalErrorException;
import org.pay_my_buddy.core.framework.domain.exception.SystemException;
import org.pay_my_buddy.core.framework.domain.message.Event;
import org.pay_my_buddy.core.framework.domain.message.EventId;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;

public abstract class AbstractAggregateRoot<AGGREGATE, ID extends EntityId> implements Aggregate<AGGREGATE>, Serializable, Cloneable {

    @Getter
    @Accessors(fluent = true)
    protected final ID id;
    private final AGGREGATE data;
    private final List<EventWrapper> uncommitedChanges = new ArrayList<>();
    private final List<EventWrapper> commitedChanges = new ArrayList<>();

    protected AbstractAggregateRoot(ID id, AGGREGATE data) {
        this.id = id;
        this.data = data;
    }


    public AGGREGATE data(){
        return data;
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
        resetAggregate();
        replayEvents();
    }

    public void addEvent(Event event) {
        final AggregateEventWrapper wrappedEvent = new AggregateEventWrapper(event, this.id, this.getClass().getName(), uncommitedChanges.size());
        applyChanges(event);
        uncommitedChanges.add(wrappedEvent);
    }

    public void recreateAggregate(List<EventWrapper> eventWrappers) {
        List<EventWrapper> events = new ArrayList<>(eventWrappers);
        events.sort(Comparator.comparing(EventWrapper::index));
        final Optional<EventWrapper> lastSnapshot = events.stream()
                .filter(e -> e.aggregateType().equals(CreateSnapshotAggregateEvent.class.getName()))
                .reduce((a, b) -> b);
        final List<EventWrapper> subList = lastSnapshot.map(eventWrapper -> events.subList(eventWrapper.index(), events.size())).orElse(events);

        commitedChanges.clear();
        commitedChanges.addAll(subList);
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

    public void createSnapshot() {
        try {
            addEvent(new CreateSnapshotAggregateEvent<>(this.data()));
        } catch (Exception e) {
            throw new InternalErrorException(e.getMessage(), e);
        }
    }

    public abstract void on(CreateSnapshotAggregateEvent<AGGREGATE> event);

    protected abstract void resetAggregate();

    protected record AggregateEventWrapper(
            EventId eventId,
            LocalDateTime timestamp,
            int index,
            EntityId aggregateId,
            String aggregateType,
            String eventType,
            Event event) implements EventWrapper {

        public AggregateEventWrapper(Event event, EntityId aggregateId, String aggregateType, int index) {
            this(event.eventId(), LocalDateTime.now(Clock.systemUTC()), index,
                    aggregateId,
                    aggregateType,
                    event.getClass().getName(), event);
        }
    }

    private <EVENT extends Event> void applyChanges(EVENT event) {
        Arrays.stream(this.getClass().getMethods())
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
            throw SystemException.wrap(new IllegalArgumentException("Error applying event", e));
        }
    }


    public record CreateSnapshotAggregateEvent<AGGREGATE>(
            EventId eventId,
            AGGREGATE aggregate) implements Event {

        public CreateSnapshotAggregateEvent(AGGREGATE aggregate) {
            this(new EventId(), aggregate);
        }
    }

}

