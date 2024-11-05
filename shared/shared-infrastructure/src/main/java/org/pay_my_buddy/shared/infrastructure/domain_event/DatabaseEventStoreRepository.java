package org.pay_my_buddy.shared.infrastructure.domain_event;


import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.domain.api.events.EventModel;
import org.pay_my_buddy.shared.domain.api.events.EventStoreRepository;
import org.pay_my_buddy.shared.domain.entity.EntityId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseEventStoreRepository implements EventStoreRepository {

    private final JpaEventStoreRepository repository;

    @Override
    public List<EventModel> findByAggregateIdentifier(EntityId aggregateIdentifier){
        return repository.findAllByAggregateIdentifier(aggregateIdentifier.toString())
                .stream()
                .map(eventModel -> (EventModel) eventModel)
                .toList();
    }

    @Override
    public void save(EventModel eventModel) {
        var entity = new EventModelEntity(eventModel);
        repository.save(entity);
    }

    @Override
    public List<EventModel> findAll() {
        return repository.findAll()
                .stream()
                .map(eventModel -> (EventModel) eventModel)
                .toList();
    }
}
