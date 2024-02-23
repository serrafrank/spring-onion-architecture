package com.example.paymybuddy.core.transaction.valueobject;

import com.example.paymybuddy.core.common.entity.id.GenericUUID;
import com.example.paymybuddy.core.common.entity.id.Id;
import lombok.NonNull;

public class TransactionId extends GenericUUID {
    public TransactionId(@NonNull Id id) {
        super(id);
    }
}
