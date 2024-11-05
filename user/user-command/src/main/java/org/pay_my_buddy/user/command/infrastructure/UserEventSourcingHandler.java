package org.pay_my_buddy.user.command.infrastructure;

import org.pay_my_buddy.shared.command.domain.events.EventStore;
import org.pay_my_buddy.shared.command.infrastructure.AbstractEventSourcingHandler;
import org.pay_my_buddy.shared.common.application.MessagePublisher;
import org.pay_my_buddy.user.command.domain.UserAggregate;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class UserEventSourcingHandler extends AbstractEventSourcingHandler<UserAggregate> {


	public UserEventSourcingHandler(EventStore eventStore, MessagePublisher eventProducer) {
		super(eventStore, eventProducer);
	}

	@Override
	protected UserAggregate newAggregateInstance() {
		return UserAggregate.newInstance();
	}

	@Override
	protected Predicate<UserAggregate> republishEventFilter(){
		return userAggregate -> userAggregate.currentState() != UserAggregate.State.CLOSE;
	}
}
