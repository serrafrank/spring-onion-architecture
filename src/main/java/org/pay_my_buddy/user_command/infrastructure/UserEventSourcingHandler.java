package org.pay_my_buddy.user_command.infrastructure;

import org.pay_my_buddy.api_command.EventStore;
import org.pay_my_buddy.api_command.AbstractEventSourcingHandler;
import org.pay_my_buddy.api_command.MessagePublisher;
import org.pay_my_buddy.shared.EntityId;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.pay_my_buddy.user_command.application.domain.UserAggregate;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class UserEventSourcingHandler extends AbstractEventSourcingHandler<UserAggregate> {


	public UserEventSourcingHandler(EventStore eventStore, MessagePublisher eventProducer) {
		super(eventStore, eventProducer);
	}

	@Override
	protected UserAggregate newAggregateInstance(EntityId id) {
		return UserAggregate.newInstance(UserId.of(id));
	}

	@Override
	protected Predicate<UserAggregate> republishEventFilter(){
		return userAggregate -> userAggregate.currentState() != UserAggregate.State.CLOSE;
	}
}
