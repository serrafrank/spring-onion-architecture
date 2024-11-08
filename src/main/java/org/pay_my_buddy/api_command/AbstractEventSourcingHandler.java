package org.pay_my_buddy.api_command;


import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.shared.EntityId;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

@Slf4j
public abstract class AbstractEventSourcingHandler<AGGREGATE extends AggregateRoot<?, ?>> implements EventSourcingHandler<AGGREGATE> {

	private final EventStore eventStore;

	private final MessagePublisher eventProducer;

	public AbstractEventSourcingHandler(EventStore eventStore, MessagePublisher eventProducer) {
		this.eventStore = eventStore;
		this.eventProducer = eventProducer;
	}

	@Override
	public void save(AggregateRoot<?, ?> aggregate) {
		eventStore.saveEvent(aggregate.id(), aggregate.getClass(), aggregate.uncommittedChanges(), aggregate.version());
		aggregate.commitChanges();
	}

	@Override
	public AGGREGATE getById(EntityId id) {
		var events = eventStore.getEvents(id);
		var aggregate = newAggregateInstance(id);
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

	protected abstract AGGREGATE newAggregateInstance(EntityId id);

	protected Predicate<AGGREGATE> republishEventFilter() {
		return aggregate -> true;
	}
}
