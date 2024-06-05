package org.pay_my_buddy.application.use_case.transaction.query.find_transaction_by_id;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.pay_my_buddy.application.common.api.AbstractQuery;
import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.transaction.Transaction;
import org.pay_my_buddy.entity.transaction.TransactionId;

import java.util.Optional;

@Value
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
