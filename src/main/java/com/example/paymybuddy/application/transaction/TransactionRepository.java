package com.example.paymybuddy.application.transaction;

import com.example.paymybuddy.core.transaction.TransactionAggregate;
import com.example.paymybuddy.core.transaction.valueobject.TransactionId;

import java.util.Optional;

public interface TransactionRepository {

    TransactionId saveTransaction(TransactionAggregate transaction);

    Optional<TransactionAggregate> findTransaction(TransactionId id);
}
