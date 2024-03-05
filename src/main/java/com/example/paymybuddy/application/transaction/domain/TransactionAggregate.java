package com.example.paymybuddy.application.transaction.domain;

import com.example.paymybuddy.application.shared.entity.id.Id;
import com.example.paymybuddy.application.shared.entity.aggregate.AbstractAggregateEntity;
import com.example.paymybuddy.application.shared.value_object.Amount;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * This class represents a transaction in the system.
 * It extends the AbstractAggregateEntity class with a type parameter of TransactionId.
 * It is annotated with @Value and @EqualsAndHashCode(callSuper = true) from the Lombok library to automatically generate getters, setters, equals, and hashCode methods.
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class TransactionAggregate extends AbstractAggregateEntity<TransactionId> implements Serializable {

    /**
     * The ID of the debtor in the transaction.
     */
    transient Id debtorId;

    /**
     * The ID of the creditor in the transaction.
     */
    transient Id creditorId;

    /**
     * The amount of the transaction.
     */
    Amount amount;

    /**
     * The date and time of the transaction.
     * It is initialized with the current date and time.
     */
    OffsetDateTime transactionDate = OffsetDateTime.now();

    /**
     * Constructor that accepts the ID of the transaction, the ID of the debtor, the ID of the creditor, and the amount of the transaction.
     * @param debtorId The ID of the debtor.
     * @param creditorId The ID of the creditor.
     * @param amount The amount of the transaction.
     */
    public TransactionAggregate(@NonNull Id debtorId, @NonNull Id creditorId, @NonNull Amount amount) {
        this.debtorId = debtorId;
        this.creditorId = creditorId;
        this.amount = amount;
    }

}