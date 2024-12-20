package org.pay_my_buddy.modules.money_account.command.infrastructure;

import org.pay_my_buddy.core.framework.domain.MessagePublisher;
import org.pay_my_buddy.core.command.infrastructure.event_sourcing.AbstractEventSourcingStorage;
import org.pay_my_buddy.core.command.infrastructure.event_sourcing.EventSourcingRepository;
import org.pay_my_buddy.modules.money_account.command.domain.MoneyAccountAggregate;
import org.pay_my_buddy.modules.money_account.shared.MoneyAccountId;
import org.springframework.stereotype.Service;

@Service
public class MoneyAccountAggregateStorage extends AbstractEventSourcingStorage<MoneyAccountAggregate, MoneyAccountId> {

    public MoneyAccountAggregateStorage(EventSourcingRepository repository, MessagePublisher eventProducer) {
        super(repository, eventProducer);
    }

}
