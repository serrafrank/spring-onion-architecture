package org.pay_my_buddy.shared.infrastructure.domain_event;


import org.pay_my_buddy.shared.domain.api.events.EventModel;
import org.pay_my_buddy.shared.domain.entity.EntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaEventStoreRepository extends JpaRepository<EventModelEntity, String> {

    List<EventModelEntity> findAllByAggregateIdentifier(String aggregateIdentifier);
    List<EventModelEntity> findAll();

}
