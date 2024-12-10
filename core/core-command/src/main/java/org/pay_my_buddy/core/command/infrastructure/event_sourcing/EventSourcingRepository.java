package org.pay_my_buddy.core.command.infrastructure.event_sourcing;

import java.util.List;
import org.pay_my_buddy.core.command.domain.event_storage.EventWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventSourcingRepository extends JpaRepository<EventWrapperEntity, String> {
    List<EventWrapper> findAllByAggregateId(String aggregateId);
}
