package com.example.paymybuddy.application.transaction.spi;

import com.example.paymybuddy.application.transaction.domain.TransactionAggregate;
import com.example.paymybuddy.application.transaction.domain.TransactionId;

import java.util.Optional;

public interface TransactionRepository {

    boolean areFriends(TransactionId debtorId, TransactionId creditorId);

    Optional<TransactionAggregate> findTransaction(TransactionId id);

    TransactionId save(TransactionAggregate transaction);

}
