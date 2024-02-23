package com.example.paymybuddy.application.transaction;

import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.common.valueobject.Amount;
import com.example.paymybuddy.core.transaction.TransactionAggregate;
import com.example.paymybuddy.core.transaction.valueobject.TransactionId;

import java.util.Optional;

public interface TransactionService {
    Optional<TransactionAggregate> findTransaction(TransactionId id);

    TransactionId createTransaction(Id debtorId, Id creditorId, Amount amount);
}
