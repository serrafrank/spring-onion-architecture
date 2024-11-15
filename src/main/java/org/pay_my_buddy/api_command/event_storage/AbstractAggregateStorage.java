package org.pay_my_buddy.api_command.event_storage;

import java.lang.reflect.ParameterizedType;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.api_command.AggregateRoot;
import org.pay_my_buddy.api_command.AggregateStorage;
import org.pay_my_buddy.api_command.MessagePublisher;
import org.pay_my_buddy.shared.EntityId;


@RequiredArgsConstructor
@Slf4j
public class AbstractAggregateStorage<AGGREGATE extends AggregateRoot<ID>, ID extends EntityId> implements AggregateStorage<AGGREGATE, ID> {

	private final AggregateRepository<AGGREGATE, ID> repository;
	private final MessagePublisher eventProducer;
	private final String aggregateType = ((Class<AGGREGATE>) ((ParameterizedType)getClass().getGenericSuperclass())
			.getActualTypeArguments()[0]).getName();

	@Override
	public void save(AGGREGATE aggregate){
		aggregate.uncommitedChanges().forEach(eventProducer::publish);
		aggregate.commitChanges();
		final AggregateEntity<AGGREGATE, ID> wrapper = new AggregateEntity<>(aggregate);
		repository.save(wrapper);
	}

	@Override
	public AGGREGATE getById(ID aggregateId){
		var id = aggregateId.value();
		return repository.findByIdAndAggregateType(id, aggregateType)
				.map(AggregateEntity::aggregate)
				.orElseThrow(() -> new AggregateNotFoundException("No aggregate found for id " + id));
	}

	@Override
	public void republishEvents(){
		repository.findAllByAggregateType(aggregateType)
				.stream()
				.map(AggregateEntity::aggregate)
				.filter(republishEventsFilter())
				.forEach(this::republishAggregateEvents);
	}

	@Override
	public Predicate<AGGREGATE> republishEventsFilter(){
		return aggregate -> true ;
	}

	private void republishAggregateEvents(AGGREGATE aggregate) {
		log.info("Republishing {} events for aggregate {}-{}", aggregate.commitedChanges().size(), aggregate.getClass().getSimpleName(), aggregate.id());
		aggregate.commitedChanges().forEach(eventProducer::publish);
	}
}
