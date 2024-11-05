package org.pay_my_buddy.user.common.domain;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;
import org.pay_my_buddy.shared.common.domain.api.BaseEvent;
import org.pay_my_buddy.shared.common.domain.entity.EntityId;

@EqualsAndHashCode(callSuper = true)
@Value
@Accessors(fluent = true)
public class UserFriendAddedEvent extends BaseEvent {
	EntityId user;
	EntityId friend;
}
