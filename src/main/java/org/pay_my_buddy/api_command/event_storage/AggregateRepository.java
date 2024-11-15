package org.pay_my_buddy.api_command.event_storage;


import java.util.List;
import java.util.Optional;
import org.pay_my_buddy.api_command.AggregateRoot;
import org.pay_my_buddy.shared.EntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregateRepository<AGGREGATE extends AggregateRoot<ID>, ID extends EntityId> extends JpaRepository<AggregateEntity<AGGREGATE, ID>, String> {

	Optional<AggregateEntity<AGGREGATE, ID>> findByIdAndAggregateType(String id, String aggregateType);

	List<AggregateEntity<AGGREGATE, ID>> findAllByAggregateType(String aggregateType);

}
