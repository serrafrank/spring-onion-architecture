package org.pay_my_buddy.user_command.application.infrastructure;

import org.pay_my_buddy.api_command.MessagePublisher;
import org.pay_my_buddy.api_command.event_storage.AbstractEventSourcingStorage;
import org.pay_my_buddy.api_command.event_storage.EventSourcingRepository;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.pay_my_buddy.user_command.application.domain.UserAggregate;
import org.springframework.stereotype.Service;

@Service
public class UserAggregateStorage extends AbstractEventSourcingStorage<UserAggregate, UserId> {

    public UserAggregateStorage(EventSourcingRepository repository, MessagePublisher eventProducer) {
        super(repository, eventProducer);
    }

}
