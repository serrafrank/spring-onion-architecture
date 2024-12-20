package org.pay_my_buddy.modules.money_account.command.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.core.command.domain.AbstractAggregateRoot;
import org.pay_my_buddy.core.framework.domain.exception.BadArgumentException;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;
import org.pay_my_buddy.modules.money_account.shared.MoneyAccountId;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("MoneyAccountAggregate unit tests")
class MoneyAccountAggregateTest {

    private MoneyAccountAggregate aggregate;

    @BeforeEach
    void setUp() {
        // GIVEN: A new MoneyAccountAggregate instance
        aggregate = MoneyAccountAggregate.newInstance(new MoneyAccountId());
    }

    @Test
    @DisplayName("GIVEN valid inputs WHEN create is called THEN aggregate is initialized")
    void testCreate() {
        // GIVEN
        final EntityId userId = new TestEntityId();
        final Currency currency = Currency.getInstance("USD");

        // WHEN
        aggregate.create(userId, currency);

        // THEN
        MoneyAccountAggregate.MoneyAccountAggregateData data = aggregate.data();
        assertThat(data).isNotNull();
        assertThat(data.currency()).isEqualTo(currency);
        assertThat(data.userId()).isEqualTo(userId);
        assertThat(data.balance()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("GIVEN valid inputs WHEN creditBalance is called THEN balance is updated")
    void testCreditBalance() {
        // GIVEN
        final Currency currency = Currency.getInstance("USD");
        final BigDecimal amount = BigDecimal.valueOf(100);

        aggregate.create(new TestEntityId(), currency);

        // WHEN
        aggregate.creditBalance(amount, currency);

        // THEN
        assertThat(aggregate.data().balance()).isEqualTo(amount);
    }

    @Test
    @DisplayName("GIVEN mismatched currency WHEN creditBalance is called THEN an exception is thrown")
    void testCreditBalanceInvalidCurrency() {
        // GIVEN
        final Currency accountCurrency = Currency.getInstance("USD");
        final Currency otherCurrency = Currency.getInstance("EUR");
        final BigDecimal amount = BigDecimal.valueOf(100);

        aggregate.create(new TestEntityId(), accountCurrency);

        // WHEN/THEN
        assertThatThrownBy(() -> aggregate.creditBalance(amount, otherCurrency))

                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class)
                .hasMessageContaining("Currency must be the same");
    }

    @Test
    @DisplayName("GIVEN valid inputs WHEN debitBalance is called THEN balance is reduced")
    void testDebitBalance() {
        // GIVEN
        final Currency currency = Currency.getInstance("USD");
        final BigDecimal initialAmount = BigDecimal.valueOf(100);
        final BigDecimal debitAmount = BigDecimal.valueOf(50);

        aggregate.create(new TestEntityId(), currency);
        aggregate.creditBalance(initialAmount, currency);

        // WHEN
        aggregate.debitBalance(debitAmount, currency);

        // THEN
        assertThat(aggregate.data().balance()).isEqualTo(initialAmount.subtract(debitAmount));
    }

    @Test
    @DisplayName("GIVEN insufficient balance WHEN debitBalance is called THEN an exception is thrown")
    void testDebitBalanceInsufficientFunds() {
        // GIVEN
        final Currency currency = Currency.getInstance("USD");
        final BigDecimal initialAmount = BigDecimal.valueOf(50);
        final BigDecimal debitAmount = BigDecimal.valueOf(100);

        aggregate.create(new TestEntityId(), currency);
        aggregate.creditBalance(initialAmount, currency);

        // WHEN/THEN
        assertThatThrownBy(() -> aggregate.debitBalance(debitAmount, currency))
                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class)
                .hasMessageContaining("Amount must be less than balance");
    }

    @Test
    @DisplayName("GIVEN mismatched currency WHEN debitBalance is called THEN an exception is thrown")
    void testDebitBalanceInvalidCurrency() {
        // GIVEN
        final Currency accountCurrency = Currency.getInstance("USD");
        final Currency otherCurrency = Currency.getInstance("EUR");
        final BigDecimal debitAmount = BigDecimal.valueOf(50);

        aggregate.create(new TestEntityId(), accountCurrency);

        // WHEN/THEN
        assertThatThrownBy(() -> aggregate.debitBalance(debitAmount, otherCurrency))

                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class)
                .hasMessageContaining("Currency must be the same");
    }

    @Test
    @DisplayName("GIVEN a snapshot event WHEN onSnapshot is called THEN aggregate is restored")
    void testRestoreSnapshot() {
        // GIVEN
        MoneyAccountAggregate.MoneyAccountAggregateData snapshotData = new MoneyAccountAggregate.MoneyAccountAggregateData();
        snapshotData.initAggregate(new TestEntityId(), Currency.getInstance("USD"), BigDecimal.valueOf(200));

        AbstractAggregateRoot.CreateSnapshotAggregateEvent<MoneyAccountAggregate.MoneyAccountAggregateData> snapshotEvent =
                new AbstractAggregateRoot.CreateSnapshotAggregateEvent<>(snapshotData);

        // WHEN
        aggregate.on(snapshotEvent);

        // THEN
        MoneyAccountAggregate.MoneyAccountAggregateData data = aggregate.data();
        assertThat(data.userId()).isEqualTo(snapshotData.userId());
        assertThat(data.currency()).isEqualTo(snapshotData.currency());
        assertThat(data.balance()).isEqualTo(snapshotData.balance());
    }

    @Test
    @DisplayName("GIVEN a created account WHEN resetAggregate is called THEN aggregate is cleared")
    void testResetAggregate() {
        // GIVEN
        final Currency currency = Currency.getInstance("USD");
        aggregate.create(new TestEntityId(), currency);
        aggregate.creditBalance(BigDecimal.valueOf(100), currency);

        // WHEN
        aggregate.resetAggregate();

        // THEN
        MoneyAccountAggregate.MoneyAccountAggregateData data = aggregate.data();
        assertThat(data.userId()).isNull();
        assertThat(data.currency()).isNull();
        assertThat(data.balance()).isEqualTo(BigDecimal.ZERO);
    }

    record TestEntityId(String value) implements EntityId {
        private final static String PREFIX = "TEST_";

        public TestEntityId() {
            this(PREFIX + EntityId.generateId());
        }

        @Override
        public String prefix() {
            return PREFIX;
        }
    }

}
