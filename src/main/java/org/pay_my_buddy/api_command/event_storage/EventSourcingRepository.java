package org.pay_my_buddy.api_command.event_storage;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventSourcingRepository extends JpaRepository<EventWrapperEntity, String> {
    List<EventWrapper> findAllByAggregateId(String aggregateId);
}
