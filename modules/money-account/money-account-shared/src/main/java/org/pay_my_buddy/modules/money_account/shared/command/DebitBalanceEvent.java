package org.pay_my_buddy.modules.money_account.shared.command;

import org.pay_my_buddy.core.framework.domain.message.Event;
import org.pay_my_buddy.core.framework.domain.message.EventId;
import org.pay_my_buddy.modules.money_account.shared.MoneyAccountId;

import java.math.BigDecimal;
import java.util.Currency;

public record DebitBalanceEvent(EventId eventId, MoneyAccountId id, BigDecimal amount, Currency currency) implements Event {

    public DebitBalanceEvent(MoneyAccountId id, BigDecimal amount, Currency currency) {
        this(new EventId(), id, amount, currency);
    }
}
