package com.example.paymybuddy.application.transaction.use_case.create_transaction;

import com.example.paymybuddy.application.shared.entity.id.Id;
import com.example.paymybuddy.application.shared.use_case.command.AbstractCommandUseCase;
import com.example.paymybuddy.application.shared.value_object.Amount;
import com.example.paymybuddy.application.transaction.spi.TransactionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateTransactionUseCase extends AbstractCommandUseCase<CreateTransactionCommand> {

    private final TransactionRepository transactionRepository;

    @Override
    public void execute(CreateTransactionCommand createTransactionCommand) {

        final Id debtorId = createTransactionCommand.debtorId();
        final Id creditorId = createTransactionCommand.creditorId();
        final Amount amount = createTransactionCommand.amount();

        if (debtorId.equals(creditorId)) {
            throw new IllegalArgumentException("Debtor and creditor cannot be the same");
        }
/*
        final boolean areFriends = userQuery.areFriends(debtorId, creditorId);
        if (!areFriends) {
            throw new IllegalArgumentException("Debtor and creditor are not friends");
        }

        userCommandApi.debitUser(new DebitAccountCommand(debtorId, amount));
        userCommandApi.creditUser(new CreditAccountCommand(creditorId, amount));

        TransactionAggregate transaction = new TransactionAggregate(debtorId, creditorId, amount);

        transactionRepository.save(transaction);

 */

    }


}
