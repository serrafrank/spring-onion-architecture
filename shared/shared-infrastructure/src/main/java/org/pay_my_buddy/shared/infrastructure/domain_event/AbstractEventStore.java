package org.pay_my_buddy.shared.infrastructure.domain_event;


import org.pay_my_buddy.shared.domain.api.MessagePublisher;
import org.pay_my_buddy.shared.domain.api.events.*;
import org.pay_my_buddy.shared.domain.entity.EntityId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbstractEventStore implements EventStore {

	private final MessagePublisher eventProducer;

	private final EventStoreRepository eventStoreRepository;

	public AbstractEventStore(MessagePublisher eventProducer, EventStoreRepository eventStoreRepository) {
		this.eventProducer = eventProducer;
		this.eventStoreRepository = eventStoreRepository;
	}

	@Override
	public void saveEvent(EntityId aggregateId, Class<?> aggregateClass, Iterable<Event> events, int expectedVersion) {
//		final List<EventModel> eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
//		if (expectedVersion != 0 && eventStream.getLast().version() != expectedVersion) {
//			throw new ConcurrencyException();
//		}

		for (Event event : events) {
			event.incrementVersion();
			final EventModel eventModel = new EventModelEntity(
					aggregateId,
					aggregateClass.getTypeName(),
					expectedVersion,
					event.getClass().getTypeName(),
					event
			);

			eventStoreRepository.save(eventModel);

			eventProducer.publish(event);
		}
	}

	@Override
	public List<Event> getEvents(EntityId aggregateId) {
		List<EventModel> eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
		if (eventStream.isEmpty()) {
			throw new AggregateNotFoundException("Aggregate not found");
		}
		return eventStream
				.stream()
				.map(EventModel::eventData)
				.toList();
	}

	@Override
	public List<EntityId> getAggregateIds() {
		return eventStoreRepository.findAll()
				.stream()
				.map(EventModel::aggregateIdentifier)
				.toList();
	}
}
