package com.example.paymybuddy.application.transaction.command;

import com.example.paymybuddy.core.common.entity.id.Id;
import org.springframework.validation.annotation.Validated;

public interface TransactionCommand {

    Id createTransaction(CreateTransactionCommand createTransactionCommand);
}
