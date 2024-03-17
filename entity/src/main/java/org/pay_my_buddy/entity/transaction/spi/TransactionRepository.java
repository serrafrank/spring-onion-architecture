package org.pay_my_buddy.entity.transaction.spi;

import org.pay_my_buddy.entity.transaction.Transaction;
import org.pay_my_buddy.entity.transaction.TransactionId;

import java.util.Optional;

public interface TransactionRepository {

    Optional<Transaction> findTransaction(TransactionId id);

    TransactionId save(Transaction transaction);

}
