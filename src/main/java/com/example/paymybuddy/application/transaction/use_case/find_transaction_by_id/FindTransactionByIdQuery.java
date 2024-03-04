package com.example.paymybuddy.application.transaction.use_case.find_transaction_by_id;

import com.example.paymybuddy.application.Query;
import com.example.paymybuddy.application.transaction.domain.TransactionAggregate;
import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.application.transaction.domain.TransactionId;

import java.util.Optional;

public record FindTransactionByIdQuery(TransactionId id) implements Query<Optional<TransactionAggregate>> {

        public FindTransactionByIdQuery(Id id) {
            this(new TransactionId(id));
        }
}
