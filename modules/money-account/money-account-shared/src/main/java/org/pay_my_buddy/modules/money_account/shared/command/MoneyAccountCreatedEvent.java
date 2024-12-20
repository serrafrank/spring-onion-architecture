package org.pay_my_buddy.modules.money_account.shared.command;

import org.pay_my_buddy.core.framework.domain.message.Event;
import org.pay_my_buddy.core.framework.domain.message.EventId;
import org.pay_my_buddy.core.framework.domain.validator.Validate;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;
import org.pay_my_buddy.modules.money_account.shared.MoneyAccountId;

import java.util.Currency;

public record MoneyAccountCreatedEvent(EventId eventId, MoneyAccountId id, EntityId userId, Currency currency ) implements Event {


    public MoneyAccountCreatedEvent(MoneyAccountId id, EntityId userId, Currency currency) {
        this(new EventId(), id, userId, currency);
    }

    public MoneyAccountCreatedEvent {
        Validate.checkIf(id).isNotNull("Money account id is required");
        Validate.checkIf(userId).isNotNull("User id is required");
        Validate.checkIf(currency).isNotNull("Currency is required");
    }
}
