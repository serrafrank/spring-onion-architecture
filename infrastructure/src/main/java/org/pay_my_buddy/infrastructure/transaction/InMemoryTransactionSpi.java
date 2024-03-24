package org.pay_my_buddy.infrastructure.transaction;

import org.pay_my_buddy.entity.transaction.Transaction;
import org.pay_my_buddy.entity.transaction.TransactionId;
import org.pay_my_buddy.entity.transaction.spi.TransactionSpi;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryTransactionSpi implements TransactionSpi {

    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public Optional<Transaction> findById(TransactionId id) {
        return
                transactions.stream()
                        .filter(transaction -> transaction.getId().equals(id))
                        .reduce((a, b) -> {
                            throw new IllegalStateException("Multiple transactions found for id: " + id);
                        });
    }

    @Override
    public void save(Transaction transaction) {
        transactions.stream()
                .filter(t -> t.getId().equals(transaction.getId()))
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple transactions found for id: " + transaction.getId());
                })
                .ifPresentOrElse(
                        t -> {
                            transactions.remove(t);
                            transactions.add(transaction);
                        },
                        () -> transactions.add(transaction)
                );
    }
}
