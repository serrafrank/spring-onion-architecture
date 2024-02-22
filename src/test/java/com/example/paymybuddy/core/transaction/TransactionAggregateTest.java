package com.example.paymybuddy.core.transaction;

import com.example.paymybuddy.core.common.entity.CurrencyCode;
import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.common.valueobject.Amount;
import com.example.paymybuddy.core.user.valueobject.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionAggregateTest {

    private TransactionAggregate transactionAggregate;
    private Id debtorId;
    private Id creditorId;
    private Amount amount;

    @BeforeEach
    void setUp() {
        debtorId = new UserId();
        creditorId = new UserId();
        amount = new Amount(BigDecimal.valueOf(100), CurrencyCode.EUR);
        transactionAggregate = new TransactionAggregate(debtorId, creditorId, amount);
    }

    @Test
    void shouldCreateTransactionWithGivenParameters() {
        assertEquals(debtorId, transactionAggregate.getDebtorId());
        assertEquals(creditorId, transactionAggregate.getCreditorId());
        assertEquals(amount, transactionAggregate.getAmount());
    }

    @Test
    void shouldCreateTransactionWithCurrentDateTime() {
        OffsetDateTime now = OffsetDateTime.now();
        assertTrue(transactionAggregate.getTransactionDate().isAfter(now.minusSeconds(1)));
        assertTrue(transactionAggregate.getTransactionDate().isBefore(now.plusSeconds(1)));
    }

    @Test
    void shouldThrowExceptionWhenDebtorIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new TransactionAggregate(null, creditorId, amount));
    }

    @Test
    void shouldThrowExceptionWhenCreditorIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new TransactionAggregate(debtorId, null, amount));
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new TransactionAggregate(debtorId, creditorId, null));
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNegative() {
        final BigDecimal negativeAmount = BigDecimal.valueOf(-100);
        Executable executable = () -> new TransactionAggregate(debtorId, creditorId, new Amount(negativeAmount, CurrencyCode.EUR));
        assertThrows(IllegalArgumentException.class, executable);
    }
}