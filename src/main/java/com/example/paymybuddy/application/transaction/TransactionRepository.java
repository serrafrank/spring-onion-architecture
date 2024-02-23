package com.example.paymybuddy.application.transaction;

import com.example.paymybuddy.core.transaction.TransactionAggregate;
import com.example.paymybuddy.core.transaction.valueobject.TransactionId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository {

    TransactionId saveTransaction(TransactionAggregate transaction);

    Optional<TransactionAggregate> findTransaction(TransactionId id);
}
