package com.example.paymybuddy.core.transaction.valueobject;

import com.example.paymybuddy.core.common.entity.id.GenericUUID;
import com.example.paymybuddy.core.common.entity.id.Id;

public class TransactionId extends GenericUUID {
    public TransactionId(Id id) {
        super(id);
    }
}
