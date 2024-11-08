package org.pay_my_buddy.shared.exchange.user.command;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;
import org.pay_my_buddy.api_command.BaseEvent;
import org.pay_my_buddy.shared.exchange.user.UserId;

@Value
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
public class UserDeletedEvent extends BaseEvent {
	UserId id;
}
