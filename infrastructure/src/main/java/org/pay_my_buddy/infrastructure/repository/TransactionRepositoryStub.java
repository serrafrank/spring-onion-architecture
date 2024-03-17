package org.pay_my_buddy.infrastructure.repository;

import org.pay_my_buddy.entity.transaction.Transaction;
import org.pay_my_buddy.entity.transaction.TransactionId;
import org.pay_my_buddy.entity.transaction.spi.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TransactionRepositoryStub implements TransactionRepository {
    @Override
    public boolean areFriends(TransactionId debtorId, TransactionId creditorId) {
        return false;
    }

    @Override
    public Optional<Transaction> findTransaction(TransactionId id) {
        return Optional.empty();
    }

    @Override
    public TransactionId save(Transaction transaction) {
        return null;
    }
}
