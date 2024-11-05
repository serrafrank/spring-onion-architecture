package org.pay_my_buddy.user.infrastructure;

import org.pay_my_buddy.shared.domain.api.MessagePublisher;
import org.pay_my_buddy.shared.domain.api.events.EventStore;
import org.pay_my_buddy.shared.infrastructure.domain_event.AbstractEventSourcingHandler;
import org.pay_my_buddy.user.domain.UserAggregate;
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
