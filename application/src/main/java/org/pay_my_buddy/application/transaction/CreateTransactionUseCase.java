package org.pay_my_buddy.application.transaction;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.account.Amount;
import org.pay_my_buddy.entity.account.CreditAccountCommand;
import org.pay_my_buddy.entity.account.DebitAccountCommand;
import org.pay_my_buddy.entity.commun.api.command.CommandBus;
import org.pay_my_buddy.entity.commun.api.command.CommandHandler;
import org.pay_my_buddy.entity.commun.api.query.QueryBus;
import org.pay_my_buddy.entity.commun.entity.Id;
import org.pay_my_buddy.entity.commun.ApplicationService;
import org.pay_my_buddy.entity.transaction.CreateTransactionCommand;
import org.pay_my_buddy.entity.transaction.Transaction;
import org.pay_my_buddy.entity.transaction.TransactionRepository;
import org.pay_my_buddy.entity.user.AreFriendsQuery;

@ApplicationService
@RequiredArgsConstructor
public class CreateTransactionUseCase implements CommandHandler<CreateTransactionCommand> {

    private final TransactionRepository transactionRepository;
    private final QueryBus queryApi;
    private final CommandBus commandApi;

    @Override
    public void handle(CreateTransactionCommand createTransactionCommand) {

        final Id debtorId = createTransactionCommand.debtorId();
        final Id creditorId = createTransactionCommand.creditorId();
        final Amount amount = createTransactionCommand.amount();

        if (debtorId.equals(creditorId)) {
            throw new IllegalArgumentException("Debtor and creditor cannot be the same");
        }

        final boolean areFriends = areFriends(debtorId, creditorId);
        if (!areFriends) {
            throw new IllegalArgumentException("Debtor and creditor are not friends");
        }

        commandApi.execute(new DebitAccountCommand(debtorId, amount));
        commandApi.execute(new CreditAccountCommand(creditorId, amount));

        Transaction transaction = new Transaction(debtorId, creditorId, amount);

        transactionRepository.save(transaction);

    }

    private Boolean areFriends(Id debtorId, Id creditorId) {
        final var query = new AreFriendsQuery(debtorId, creditorId);
        return queryApi.execute(query);
    }


}
