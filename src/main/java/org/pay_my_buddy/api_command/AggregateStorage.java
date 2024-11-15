package org.pay_my_buddy.api_command;

import java.util.function.Predicate;
import org.pay_my_buddy.shared.EntityId;

public interface AggregateStorage<AGGREGATE extends AggregateRoot<ID>, ID extends EntityId> {
	void save(AGGREGATE aggregate);

	AGGREGATE getById(ID id);

	void republishEvents();

	Predicate<AGGREGATE> republishEventsFilter();
}
