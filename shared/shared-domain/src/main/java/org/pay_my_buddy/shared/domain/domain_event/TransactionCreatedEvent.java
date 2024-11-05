package org.pay_my_buddy.shared.domain.domain_event;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;
import org.pay_my_buddy.shared.domain.api.events.BaseEvent;
import org.pay_my_buddy.shared.domain.entity.EntityId;

@EqualsAndHashCode(callSuper = true)
@Value
@Accessors(fluent = true)
public class TransactionCreatedEvent extends BaseEvent {
	EntityId id;
}