package org.pay_my_buddy.modules.money_account.command.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.pay_my_buddy.core.command.domain.AbstractAggregateRoot;
import org.pay_my_buddy.core.command.domain.event_storage.AggregateEventListener;
import org.pay_my_buddy.core.framework.domain.exception.BadArgumentException;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.core.framework.domain.validator.Validate;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;
import org.pay_my_buddy.modules.money_account.shared.MoneyAccountId;
import org.pay_my_buddy.modules.money_account.shared.command.CreditBalanceEvent;
import org.pay_my_buddy.modules.money_account.shared.command.DebitBalanceEvent;
import org.pay_my_buddy.modules.money_account.shared.command.MoneyAccountCreatedEvent;

import java.math.BigDecimal;
import java.util.Currency;

public class MoneyAccountAggregate extends AbstractAggregateRoot<MoneyAccountAggregate.MoneyAccountAggregateData, MoneyAccountId> {

    @Getter
    @Accessors(fluent = true)
    @NoArgsConstructor
    public static class MoneyAccountAggregateData {
        private EntityId userId;
        private Currency currency;
        private BigDecimal balance;

        public void initAggregate(EntityId userId, Currency currency, BigDecimal balance) {
            this.userId = userId;
            this.currency = currency;
            this.balance = balance;
        }

        public void resetAggregate() {
            this.userId = null;
            this.currency = null;
            this.balance = BigDecimal.ZERO;
        }

        public void resetAggregate(MoneyAccountAggregateData aggregate) {
            this.userId = aggregate.userId;
            this.currency = aggregate.currency;
            this.balance = aggregate.balance;
        }

    }

    private MoneyAccountAggregate(MoneyAccountId id) {
        super(id, new MoneyAccountAggregateData());
    }

    private MoneyAccountAggregate() {
        this(new MoneyAccountId());
    }

    public static MoneyAccountAggregate newInstance(MoneyAccountId id) {
        return new MoneyAccountAggregate(id);
    }

    public static MoneyAccountAggregate newInstance() {
        return new MoneyAccountAggregate();
    }

    public MoneyAccountAggregate create(EntityId userId, Currency currency) {
        final var event = new MoneyAccountCreatedEvent(id(), userId, currency);
        addEvent(event);
        return this;
    }

    public MoneyAccountAggregate creditBalance(BigDecimal amount, Currency currency) {
        validateData(amount, currency);

        final var event = new CreditBalanceEvent(id(), amount, currency);
        addEvent(event);
        return this;
    }

    public MoneyAccountAggregate debitBalance(BigDecimal amount, Currency currency) {
        validateData(amount, currency);

        if (amount.compareTo(this.data().balance) > 0) {
            throw BusinessException.wrap(new BadArgumentException("Amount must be less than balance"));
        }

        final var event = new DebitBalanceEvent(id(), amount, currency);
        addEvent(event);
        return this;
    }

    @AggregateEventListener
    public void on(MoneyAccountCreatedEvent event) {
        this.data().initAggregate(event.userId(), event.currency(), BigDecimal.ZERO);
    }

    @AggregateEventListener
    public void on(CreditBalanceEvent event) {
        this.data().balance = this.data().balance.add(event.amount());
    }

    @AggregateEventListener
    public void on(DebitBalanceEvent event) {
        this.data().balance = this.data().balance.subtract(event.amount());
    }

    @AggregateEventListener
    @Override
    public void on(CreateSnapshotAggregateEvent<MoneyAccountAggregate.MoneyAccountAggregateData> event) {
        this.data().resetAggregate(event.aggregate());
    }

    @Override
    protected void resetAggregate() {
        this.data().resetAggregate();
    }

    private void validateData(BigDecimal amount, Currency currency) {
        if (amount == null) {
            throw BusinessException.wrap(new BadArgumentException("Amount is required"));
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw BusinessException.wrap(new BadArgumentException("Amount must be positive"));
        }

        if (currency == null) {
            throw BusinessException.wrap(new BadArgumentException("Currency is required"));
        }

        if (currency != this.data().currency) {
            throw BusinessException.wrap(new BadArgumentException("Currency must be the same"));
        }
    }

}
