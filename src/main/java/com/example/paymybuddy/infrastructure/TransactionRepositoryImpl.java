package com.example.paymybuddy.infrastructure;

import com.example.paymybuddy.application.transaction.TransactionRepository;
import com.example.paymybuddy.core.transaction.TransactionAggregate;
import com.example.paymybuddy.core.transaction.valueobject.TransactionId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
    @Override
    public TransactionId saveTransaction(TransactionAggregate transaction) {
        return null;
    }

    @Override
    public Optional<TransactionAggregate> findTransaction(TransactionId id) {
        return Optional.empty();
    }
}
