package org.pay_my_buddy.core.command.infrastructure.event_sourcing;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.command.domain.AbstractAggregateRoot;
import org.pay_my_buddy.core.command.domain.event_storage.AggregateNotFoundException;
import org.pay_my_buddy.core.command.domain.event_storage.EventSourcingStorage;
import org.pay_my_buddy.core.command.domain.event_storage.EventWrapper;
import org.pay_my_buddy.core.command.domain.event_storage.MessagePublisher;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.core.framework.domain.exception.InternalErrorException;
import org.pay_my_buddy.core.framework.domain.exception.SystemException;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class AbstractEventSourcingStorage<AGGREGATE extends AbstractAggregateRoot<?, ID>, ID extends EntityId> implements EventSourcingStorage<AGGREGATE, ID> {

    private final EventSourcingRepository repository;
    private final MessagePublisher eventProducer;

    @Override
    public AGGREGATE save(AGGREGATE aggregate) {
        aggregate.uncommitedChanges()
                .stream()
                .map(EventWrapper::event)
                .forEach(eventProducer::publish);
        aggregate.createSnapshot();
        aggregate.commitChanges();
        var eventWrapperEntities = aggregate.commitedChanges().stream()
                .map(EventWrapperEntity::new)
                .toList();
        repository.saveAll(eventWrapperEntities);
        return aggregate;
    }

    @Override
    public AGGREGATE getById(ID aggregateId) {
        final List<EventWrapper> events = repository.findAllEventsAfterLastSnapshot(aggregateId.value());
        if (events.isEmpty()) {
            throw BusinessException.wrap(new AggregateNotFoundException(aggregateId));
        }
        final AGGREGATE aggregate = newInstance(aggregateId);
        aggregate.recreateAggregate(events);
        return aggregate;
    }

    @Override
    public void republishEvents() {
        repository.findAll()
                .stream()
                .sorted(Comparator.comparing(EventWrapper::index))
                .map(EventWrapperEntity::event)
                .forEach(eventProducer::publish);
    }

    @SuppressWarnings("unchecked")
    private AGGREGATE newInstance(ID aggregateId) {
        try {
            Class<AGGREGATE> aggregateClass = ((Class<AGGREGATE>) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0]);

            Constructor<AGGREGATE> aggregateConstructor = (Constructor<AGGREGATE>) Stream.of(aggregateClass.getDeclaredConstructors())
                    .filter(constructor -> constructor.getParameterCount() == 1)
                    .filter(constructor -> constructor.getParameterTypes()[0].isAssignableFrom(aggregateId.getClass()))
                    .findFirst()
                    .orElseThrow(() -> SystemException.wrap(new InternalErrorException("No constructor found for aggregate " + aggregateClass)));

            aggregateConstructor.setAccessible(true);
            return aggregateConstructor.newInstance(aggregateId);
        } catch (Exception e) {
            throw SystemException.wrap(new InternalErrorException(e.getMessage(), e));
        }
    }
}
