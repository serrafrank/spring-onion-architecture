package com.example.paymybuddy.application.transaction.query;

import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.transaction.valueobject.TransactionId;

public record FindTransactionByIdQuery(TransactionId id) {

        public FindTransactionByIdQuery(Id id) {
            this(new TransactionId(id));
        }
}
