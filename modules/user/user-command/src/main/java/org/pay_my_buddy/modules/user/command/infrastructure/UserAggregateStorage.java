package org.pay_my_buddy.modules.user.command.infrastructure;

import org.pay_my_buddy.core.command.domain.event_storage.MessagePublisher;
import org.pay_my_buddy.core.command.infrastructure.event_sourcing.AbstractEventSourcingStorage;
import org.pay_my_buddy.core.command.infrastructure.event_sourcing.EventSourcingRepository;
import org.pay_my_buddy.modules.user.command.domain.UserAggregate;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.springframework.stereotype.Service;

@Service
public class UserAggregateStorage extends AbstractEventSourcingStorage<UserAggregate, UserId> {

    public UserAggregateStorage(EventSourcingRepository repository, MessagePublisher eventProducer) {
        super(repository, eventProducer);
    }

}
