package org.pay_my_buddy.api_command.event_storage;


import org.pay_my_buddy.api_command.Event;
import org.pay_my_buddy.api_command.EventStore;
import org.pay_my_buddy.api_command.MessagePublisher;
import org.pay_my_buddy.shared.EntityId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EventStoreImpl implements EventStore {

	private final MessagePublisher eventProducer;

	private final EventStoreRepository eventStoreRepository;

	public EventStoreImpl(MessagePublisher eventProducer, EventStoreRepository eventStoreRepository) {
		this.eventProducer = eventProducer;
		this.eventStoreRepository = eventStoreRepository;
	}

	@Override
	public void saveEvent(EntityId aggregateId, Class<?> aggregateClass, Iterable<Event> events, int expectedVersion) {

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
			throw new AggregateNotFoundException("No events found for aggregate " + aggregateId);
		}
		return eventStream
				.stream()
				.map(EventModel::eventData)
				.toList();
	}

	@Override
	public Set<? extends EntityId> getAggregateIds() {
		return eventStoreRepository.findAllAggregateIds();
	}
}
