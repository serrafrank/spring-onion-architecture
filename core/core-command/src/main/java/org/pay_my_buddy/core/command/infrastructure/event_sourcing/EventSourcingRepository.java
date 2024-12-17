package org.pay_my_buddy.core.command.infrastructure.event_sourcing;

import org.pay_my_buddy.core.command.domain.event_storage.EventWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventSourcingRepository extends JpaRepository<EventWrapperEntity, String> {

    @Query("SELECT e FROM EventWrapperEntity e " +
            "WHERE e.aggregateId = :aggregateId " +
            "AND e.index >= (SELECT MAX(e2.index) FROM EventWrapperEntity e2 WHERE e2.aggregateId = e.aggregateId and e2.eventType = 'CreateSnapshotAggregateEvent')" +
            "ORDER BY e.index ASC")
    List<EventWrapper> findAllEventsAfterLastSnapshot(String aggregateId);
}
