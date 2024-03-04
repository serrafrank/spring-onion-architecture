package com.example.paymybuddy.application.transaction.use_case.create_transaction;

import com.example.paymybuddy.application.account.use_case.credit_account.CreditAccountCommand;
import com.example.paymybuddy.application.account.use_case.debit_account.DebitAccountCommand;
import com.example.paymybuddy.application.transaction.CreateTransactionUseCase;
import com.example.paymybuddy.application.transaction.spi.TransactionRepository;
import com.example.paymybuddy.application.user.command.UserCommandApi;
import com.example.paymybuddy.application.user.query.AreFriendsQuery;
import com.example.paymybuddy.application.user.query.UserQuery;
import com.example.paymybuddy.application.transaction.domain.TransactionAggregate;
import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.common.value_object.Amount;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateTransactionUseCaseImpl implements CreateTransactionUseCase {

    private final TransactionRepository transactionRepository;

    @Override
    public void execute(CreateTransactionCommand createTransactionCommand) {

        final Id debtorId = createTransactionCommand.debtorId();
        final Id creditorId = createTransactionCommand.creditorId();
        final Amount amount = createTransactionCommand.amount();

        if (debtorId.equals(creditorId)) {
            throw new IllegalArgumentException("Debtor and creditor cannot be the same");
        }

        final boolean areFriends = userQuery.areFriends(debtorId, creditorId);
        if (!areFriends) {
            throw new IllegalArgumentException("Debtor and creditor are not friends");
        }

        userCommandApi.debitUser(new DebitAccountCommand(debtorId, amount));
        userCommandApi.creditUser(new CreditAccountCommand(creditorId, amount));

        TransactionAggregate transaction = new TransactionAggregate(debtorId, creditorId, amount);

        transactionRepository.save(transaction);

    }


}
