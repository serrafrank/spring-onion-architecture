package com.example.paymybuddy.application.transaction.command;

import com.example.paymybuddy.application.transaction.TransactionService;
import com.example.paymybuddy.core.common.entity.id.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionCommandImpl implements TransactionCommand {

    private final TransactionService transactionService;

    @Override
    public Id createTransaction(CreateTransactionCommand createTransactionCommand) {
        return transactionService.createTransaction(createTransactionCommand.debtorId(),
                createTransactionCommand.creditorId(),
                createTransactionCommand.amount());
    }
}
