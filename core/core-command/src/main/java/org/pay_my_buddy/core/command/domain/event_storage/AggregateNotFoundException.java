package org.pay_my_buddy.core.command.domain.event_storage;

import org.pay_my_buddy.core.framework.domain.exception.NotFoundException;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;

public class AggregateNotFoundException extends NotFoundException {
    public <ID extends EntityId> AggregateNotFoundException(ID aggregateId) {
        super("Aggregate not found : " + aggregateId);
    }
}
