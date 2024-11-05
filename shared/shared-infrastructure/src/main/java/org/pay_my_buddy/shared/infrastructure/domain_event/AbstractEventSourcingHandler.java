package org.pay_my_buddy.shared.infrastructure.domain_event;


import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.shared.domain.api.MessagePublisher;
import org.pay_my_buddy.shared.domain.api.events.Event;
import org.pay_my_buddy.shared.domain.api.events.EventSourcingHandler;
import org.pay_my_buddy.shared.domain.api.events.EventStore;
import org.pay_my_buddy.shared.domain.entity.AggregateRoot;
import org.pay_my_buddy.shared.domain.entity.EntityId;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

@Slf4j
public abstract class AbstractEventSourcingHandler<AGGREGATE extends AggregateRoot<?>> implements EventSourcingHandler<AGGREGATE> {

	private final EventStore eventStore;

	private final MessagePublisher eventProducer;

	public AbstractEventSourcingHandler(EventStore eventStore, MessagePublisher eventProducer) {
		this.eventStore = eventStore;
		this.eventProducer = eventProducer;
	}

	@Override
	public void save(AGGREGATE aggregate) {
		eventStore.saveEvent(aggregate.id(), aggregate.getClass(), aggregate.uncommittedChanges(), aggregate.version());
		aggregate.markChangesAsCommitted();
	}

	@Override
	public AGGREGATE getById(EntityId id) {
		var aggregate = newAggregateInstance();
		var events = eventStore.getEvents(id);
		if (events != null && !events.isEmpty()) {
			aggregate.replayEvents(events);
			int latestVersion = events
					.stream().map(Event::version)
					.max(Comparator.naturalOrder())
					.orElse(0);
			aggregate.version(latestVersion);
		}
		return aggregate;
	}

	@Override
	public void republishEvents() {
		eventStore.getAggregateIds()
				.stream()
				.map(this::getById)
				.filter(Objects::nonNull)
				.filter(republishEventFilter())
				.peek(aggregate -> log.info("Republishing events for aggregate {}", aggregate.id()))
				.map(aggregate -> eventStore.getEvents(aggregate.id()))
				.forEach(events -> events.forEach(eventProducer::publish));
	}

	protected abstract AGGREGATE newAggregateInstance();

	protected Predicate<AGGREGATE> republishEventFilter() {
		return aggregate -> true;
	}
}
