package com.example.paymybuddy.application.transaction.domain;

import com.example.paymybuddy.application.shared.entity.id.GenericId;
import com.example.paymybuddy.application.shared.entity.id.Id;

public class TransactionId extends GenericId {
    public TransactionId(Id id) {
        super(id);
    }
}
