package com.example.paymybuddy.application.transaction.query;

import com.example.paymybuddy.core.transaction.TransactionAggregate;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

public interface TransactionQuery {

    Optional<TransactionAggregate> findTransaction(FindTransactionByIdQuery findTransactionByIdQuery);

}
