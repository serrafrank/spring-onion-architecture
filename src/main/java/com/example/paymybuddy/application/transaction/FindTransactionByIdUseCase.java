package com.example.paymybuddy.application.transaction;

import com.example.paymybuddy.application.QueryUseCase;
import com.example.paymybuddy.application.transaction.domain.TransactionAggregate;
import com.example.paymybuddy.application.transaction.use_case.find_transaction_by_id.FindTransactionByIdQuery;

import java.util.Optional;

public interface FindTransactionByIdUseCase extends QueryUseCase<FindTransactionByIdQuery, Optional<TransactionAggregate>> {
}
