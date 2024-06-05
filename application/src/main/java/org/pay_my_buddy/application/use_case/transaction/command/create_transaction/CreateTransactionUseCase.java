package org.pay_my_buddy.application.use_case.transaction.command.create_transaction;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.application.common.DomainService;
import org.pay_my_buddy.application.common.api.ApiProvider;
import org.pay_my_buddy.application.common.api.CommandHandler;
import org.pay_my_buddy.application.common.api.CommandResponse;
import org.pay_my_buddy.application.use_case.account.command.credit_account.CreditAccountCommand;
import org.pay_my_buddy.application.use_case.account.command.debit_account.DebitAccountCommand;
import org.pay_my_buddy.application.use_case.transaction.TransactionSpi;
import org.pay_my_buddy.application.use_case.user.query.are_friends.AreFriendsQuery;
import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.amount.Amount;
import org.pay_my_buddy.entity.transaction.Transaction;

@DomainService
@RequiredArgsConstructor
public class CreateTransactionUseCase implements CommandHandler<CreateTransactionCommand, Void> {

    private final TransactionSpi transactionSpi;
    private final ApiProvider apiProvider;

    @Override
    public CommandResponse<Void> handle(CreateTransactionCommand createTransactionCommand) {

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

        return CommandResponse.empty();

    }

    private Boolean areFriends(Id debtorId, Id creditorId) {
        final AreFriendsQuery query = new AreFriendsQuery(debtorId, creditorId);
        return apiProvider.request(query);
    }


}
