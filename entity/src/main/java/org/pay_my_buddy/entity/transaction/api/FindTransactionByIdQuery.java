package org.pay_my_buddy.entity.transaction.api;

import org.pay_my_buddy.entity.commun.api.query.Query;
import org.pay_my_buddy.entity.commun.entity.Id;
import org.pay_my_buddy.entity.transaction.Transaction;
import org.pay_my_buddy.entity.transaction.TransactionId;

import java.util.Optional;

public record FindTransactionByIdQuery(TransactionId id) implements Query<Optional<Transaction>> {

    public FindTransactionByIdQuery(Id id) {
        this(TransactionId.of(id));
    }
}
