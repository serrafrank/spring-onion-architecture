package org.pay_my_buddy.user_command.application.infrastructure;

import java.util.function.Predicate;
import org.pay_my_buddy.api_command.MessagePublisher;
import org.pay_my_buddy.api_command.event_storage.AbstractAggregateStorage;
import org.pay_my_buddy.api_command.event_storage.AggregateRepository;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.pay_my_buddy.user_command.application.domain.UserAggregate;
import org.springframework.stereotype.Service;

@Service
public class UserAggregateStorage extends AbstractAggregateStorage<UserAggregate, UserId> {

	public UserAggregateStorage(AggregateRepository<UserAggregate, UserId> repository, MessagePublisher eventProducer) {
		super(repository, eventProducer);
	}

	@Override
	public Predicate<UserAggregate> republishEventsFilter(){
		return aggregate -> !aggregate.currentState().equals(UserAggregate.State.CLOSE) ;
	}
}
