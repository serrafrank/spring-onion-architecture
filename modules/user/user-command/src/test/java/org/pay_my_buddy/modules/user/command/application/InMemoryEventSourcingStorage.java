package org.pay_my_buddy.modules.user.command.application;

import org.pay_my_buddy.core.command.domain.event_storage.EventSourcingStorage;
import org.pay_my_buddy.core.command.domain.event_storage.EventWrapper;
import org.pay_my_buddy.core.framework.domain.message.Event;
import org.pay_my_buddy.core.framework.domain.message.EventId;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;
import org.pay_my_buddy.modules.user.command.domain.UserAggregate;
import org.pay_my_buddy.modules.user.shared.UserId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InMemoryEventSourcingStorage implements EventSourcingStorage<UserAggregate, UserId> {

    private final List<EventWrapper> events = new ArrayList<>();

    @Override
    public UserAggregate save(UserAggregate aggregate) {
        // Commit and snapshot creation is handled by the aggregate
        aggregate.uncommitedChanges().forEach(eventWrapper -> {
            // Publish event (no-op here)
        });
        aggregate.commitChanges();
        aggregate.createSnapshot();

        // Store events
        for (EventWrapper ew : aggregate.commitedChanges()) {
            events.add(new InMemoryEventWrapper(ew.aggregateId(), ew.event(), events.size()));
        }
        return aggregate;
    }

    @Override
    public UserAggregate getById(UserId aggregateId) {
        // Find all events for this aggregate
        final List<EventWrapper> aggregateEvents = events.stream()
                .filter(e -> e.aggregateId().equals(aggregateId))
                .sorted(Comparator.comparingInt(EventWrapper::index))
                .toList();

        if (aggregateEvents.isEmpty()) {
            throw new IllegalArgumentException("Aggregate not found");
        }

        // Recreate aggregate from events
        final UserAggregate agg = UserAggregate.newInstance(aggregateId);
        agg.recreateAggregate(aggregateEvents);
        return agg;
    }

    public UserId getByEmail(String email) {
        // Extract user from known events
        // This is a simplistic approach: we assume there's a UserCreatedEvent (or similar)
        // that sets the email and first event sets the ID.
        // We'll rely on replayed aggregates to find a matching user.
        // In a real integration test, you'd query a read model or track emails differently.
        return events.stream()
                .map(EventWrapper::aggregateId)
                .distinct()
                .filter(id -> {
                    UserAggregate agg = getById((UserId) id);
                    return email.equalsIgnoreCase(agg.email());
                })
                .map(id -> (UserId) id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No user found with email: " + email));
    }

    @Override
    public void republishEvents() {
        // no-op for this test
    }
    /**
     * InMemoryEventWrapper is a simple event wrapper holding data in memory.
     */
    public record InMemoryEventWrapper(EntityId aggregateId, Event event, int index) implements EventWrapper {
        @Override
        public EventId eventId() {
            return event.eventId();
        }

        @Override
        public LocalDateTime timestamp() {
            return LocalDateTime.now();
        }

        @Override
        public String aggregateType() {
            return "UserAggregate";
        }

        @Override
        public String eventType() {
            return event.getClass().getName();
        }
    }
}
