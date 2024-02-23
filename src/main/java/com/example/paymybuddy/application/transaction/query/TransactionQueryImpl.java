package com.example.paymybuddy.application.transaction.query;

import com.example.paymybuddy.application.transaction.TransactionService;
import com.example.paymybuddy.core.transaction.TransactionAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionQueryImpl implements TransactionQuery {

    private final TransactionService transactionService;

    @Override
    public Optional<TransactionAggregate> findTransaction(FindTransactionByIdQuery findTransactionByIdQuery) {
        return transactionService.findTransaction(findTransactionByIdQuery.id());
    }
}
