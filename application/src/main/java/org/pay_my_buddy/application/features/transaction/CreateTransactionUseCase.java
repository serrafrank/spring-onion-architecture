package org.pay_my_buddy.application.features.transaction;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.application.common.DomainService;
import org.pay_my_buddy.application.common.api.ApiProvider;
import org.pay_my_buddy.application.common.api.command.CommandHandler;
import org.pay_my_buddy.application.common.api.command.EventList;
import org.pay_my_buddy.application.features.account.api.CreditAccountCommand;
import org.pay_my_buddy.application.features.account.api.DebitAccountCommand;
import org.pay_my_buddy.application.features.transaction.spi.TransactionSpi;
import org.pay_my_buddy.application.features.user.api.AreFriendsQuery;
import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.amount.Amount;
import org.pay_my_buddy.entity.transaction.Transaction;

@DomainService
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

        apiProvider.execute(DebitAccountCommand.of(debtorId, amount));
        apiProvider.execute(CreditAccountCommand.of(creditorId, amount));

        Transaction transaction = new Transaction(debtorId, creditorId, amount);

        transactionSpi.save(transaction);

        return EventList.empty();

    }

    private Boolean areFriends(Id debtorId, Id creditorId) {
        final AreFriendsQuery query = new AreFriendsQuery(debtorId, creditorId);
        return apiProvider.request(query);
    }


}
