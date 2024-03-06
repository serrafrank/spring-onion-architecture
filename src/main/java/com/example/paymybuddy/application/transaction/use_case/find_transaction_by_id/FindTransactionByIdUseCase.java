package com.example.paymybuddy.application.transaction.use_case.find_transaction_by_id;

import com.example.paymybuddy.application.shared.message_handler.QueryHandler;
import com.example.paymybuddy.application.transaction.domain.TransactionAggregate;
import com.example.paymybuddy.application.transaction.domain.TransactionId;
import com.example.paymybuddy.application.transaction.spi.TransactionRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class FindTransactionByIdUseCase implements QueryHandler<FindTransactionByIdQuery, Optional<TransactionAggregate>> {

    private final TransactionRepository transactionRepository;

    @Override
    public Optional<TransactionAggregate> execute(FindTransactionByIdQuery findTransactionByIdQuery) {
        final TransactionId id = findTransactionByIdQuery.id();
        return transactionRepository.findTransaction(id);
    }

}
