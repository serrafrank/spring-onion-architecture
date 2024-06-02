package org.pay_my_buddy.application.transaction;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.account.api.CreditAccountCommand;
import org.pay_my_buddy.entity.account.api.DebitAccountCommand;
import org.pay_my_buddy.entity.common.ApplicationService;
import org.pay_my_buddy.entity.common.api.ApiProvider;
import org.pay_my_buddy.entity.common.api.command.CommandHandler;
import org.pay_my_buddy.entity.common.api.command.EventList;
import org.pay_my_buddy.entity.common.entity.Id;
import org.pay_my_buddy.entity.common.value_object.Amount;
import org.pay_my_buddy.entity.transaction.Transaction;
import org.pay_my_buddy.entity.transaction.api.CreateTransactionCommand;
import org.pay_my_buddy.entity.transaction.spi.TransactionSpi;
import org.pay_my_buddy.entity.user.api.AreFriendsQuery;

@ApplicationService
@RequiredArgsConstructor
public class CreateTransactionUseCase implements CommandHandler<CreateTransactionCommand> {

    private final TransactionSpi transactionSpi;
    private final ApiProvider apiProvider;

    @Override
    public EventList handle(CreateTransactionCommand createTransactionCommand) {

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

        apiProvider.execute(new DebitAccountCommand(debtorId, amount));
        apiProvider.execute(new CreditAccountCommand(creditorId, amount));

        Transaction transaction = new Transaction(debtorId, creditorId, amount);

        transactionSpi.save(transaction);

        return EventList.empty();

    }

    private Boolean areFriends(Id debtorId, Id creditorId) {
        final var query = new AreFriendsQuery(debtorId, creditorId);
        return apiProvider.request(query);
    }


}
