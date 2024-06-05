package org.pay_my_buddy.application.use_case.transaction;


import org.pay_my_buddy.entity.transaction.Transaction;
import org.pay_my_buddy.entity.transaction.TransactionId;

import java.util.Optional;

public interface TransactionSpi {

    Optional<Transaction> findById(TransactionId id);

    void save(Transaction transaction);

}
