package org.pay_my_buddy.entity.application.transaction;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;
import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.common.api.query.AbstractQuery;
import org.pay_my_buddy.entity.transaction.Transaction;
import org.pay_my_buddy.entity.transaction.TransactionId;

import java.util.Optional;

@Value
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class FindTransactionByIdQuery extends AbstractQuery<Optional<Transaction>> {
    TransactionId id;

    private FindTransactionByIdQuery(TransactionId id) {
        this.id = id;
    }

    public static FindTransactionByIdQuery of(TransactionId id) {
        return new FindTransactionByIdQuery(id);
    }

    public static FindTransactionByIdQuery of(Id id) {
        return new FindTransactionByIdQuery(TransactionId.of(id));
    }

}
