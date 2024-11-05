package org.pay_my_buddy.user.common.domain;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;
import org.pay_my_buddy.shared.common.domain.api.BaseEvent;
import org.pay_my_buddy.shared.common.domain.entity.EntityId;

@Value
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
public class UserDeletedEvent extends BaseEvent {
	EntityId id;
}
