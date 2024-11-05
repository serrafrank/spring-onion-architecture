package org.pay_my_buddy.shared.domain.domain_event;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;
import org.pay_my_buddy.shared.domain.api.events.BaseEvent;
import org.pay_my_buddy.shared.domain.entity.EntityId;

@Value
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
public class UserDeletedEvent extends BaseEvent {
	EntityId id;
}
