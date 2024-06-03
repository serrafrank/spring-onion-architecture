package org.pay_my_buddy.entity.transaction;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;
import org.pay_my_buddy.entity.AbstractModel;
import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.amount.Amount;

import java.time.OffsetDateTime;

/**
 * This class represents a transaction in the system.
 * It extends the AbstractModel class with a type parameter of TransactionId.
 * It is annotated with @Value and @EqualsAndHashCode(callSuper = true) from the Lombok library to automatically generate getters, setters, equals, and hashCode methods.
 */
@Value
@Accessors(fluent = true, chain = true)
@EqualsAndHashCode(callSuper = true)
public class Transaction extends AbstractModel<TransactionId> {

    /**
     * The ID of the debtor in the transaction.
     */
    Id debtorId;

    /**
     * The ID of the creditor in the transaction.
     */
    Id creditorId;

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
     *
     * @param debtorId   The ID of the debtor.
     * @param creditorId The ID of the creditor.
     * @param amount     The amount of the transaction.
     */
    public Transaction(@NonNull Id debtorId, @NonNull Id creditorId, @NonNull Amount amount) {
        super(TransactionId.of());
        this.debtorId = debtorId;
        this.creditorId = creditorId;
        this.amount = amount;
    }

}