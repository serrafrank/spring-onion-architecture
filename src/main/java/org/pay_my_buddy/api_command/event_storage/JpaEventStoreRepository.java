package org.pay_my_buddy.api_command.event_storage;


import com.sun.java.accessibility.util.EventID;
import org.pay_my_buddy.shared.EntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface JpaEventStoreRepository extends JpaRepository<EventModelEntity, EntityId> {

    List<EventModelEntity> findAllByAggregateIdentifier(String aggregateIdentifier);

    @Query(value = "SELECT distinct(e.id) from EventModelEntity e", nativeQuery = true)
    Set<EntityId> findAllAggregateIds();

}
