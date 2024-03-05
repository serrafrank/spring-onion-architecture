package com.example.paymybuddy.application.transaction.use_case.find_transaction_by_id;

import com.example.paymybuddy.application.shared.use_case.query.AbstractQueryUseCase;
import com.example.paymybuddy.application.transaction.spi.TransactionRepository;
import com.example.paymybuddy.application.transaction.domain.TransactionAggregate;
import com.example.paymybuddy.application.transaction.domain.TransactionId;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class FindTransactionByIdUseCase extends AbstractQueryUseCase<FindTransactionByIdQuery, Optional<TransactionAggregate>> {

    private final TransactionRepository transactionRepository;

    @Override
    public  Optional<TransactionAggregate> execute(FindTransactionByIdQuery findTransactionByIdQuery) {
        final TransactionId id = findTransactionByIdQuery.id();
        return transactionRepository.findTransaction(id);
    }

}
