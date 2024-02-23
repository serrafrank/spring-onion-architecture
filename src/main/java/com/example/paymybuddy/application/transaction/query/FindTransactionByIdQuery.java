package com.example.paymybuddy.application.transaction.query;

import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.transaction.valueobject.TransactionId;
import com.example.paymybuddy.core.validator.Constraint;

public record FindTransactionByIdQuery(TransactionId id) {

    public FindTransactionByIdQuery {
        Constraint.of(id).notNull().validate();
    }

    public FindTransactionByIdQuery(Id id) {
        this(new TransactionId(id));
    }
}
