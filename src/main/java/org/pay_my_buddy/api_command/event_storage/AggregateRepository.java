package org.pay_my_buddy.api_command.event_storage;


import org.pay_my_buddy.api_command.AggregateRoot;
import org.pay_my_buddy.shared.EntityId;

import java.util.List;
import java.util.Set;

public interface AggregateRepository {

    AggregateRoot<?, ?> load(EntityId aggregateId);

    void save(AggregateRoot<?, ?> aggregate);
}
