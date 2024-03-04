package com.example.paymybuddy.infrastructure;

import com.example.paymybuddy.application.transaction.TransactionCommandRepository;
import com.example.paymybuddy.application.transaction.domain.TransactionAggregate;
import com.example.paymybuddy.application.transaction.domain.TransactionId;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionCommandRepositoryImpl implements TransactionCommandRepository {
    @Override
    public TransactionId saveTransaction(TransactionAggregate transaction) {
        return null;
    }
}
