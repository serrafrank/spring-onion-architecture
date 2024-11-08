package org.pay_my_buddy.shared.exchange.user.query;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;
import org.pay_my_buddy.api_command.BaseEvent;
import org.pay_my_buddy.shared.exchange.user.UserId;

@EqualsAndHashCode(callSuper = true)
@Value
@Accessors(fluent = true)
public class UserFriendAddedEvent extends BaseEvent {
	UserId user;
	UserId friend;
}
