package org.pay_my_buddy.application.features.account;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.application.common.DomainService;
import org.pay_my_buddy.application.common.api.command.CommandHandler;
import org.pay_my_buddy.application.common.api.command.EventList;
import org.pay_my_buddy.application.features.account.api.DebitAccountCommand;
import org.pay_my_buddy.application.features.account.exception.AccountNotExistsException;
import org.pay_my_buddy.application.features.account.spi.AccountSpi;
import org.pay_my_buddy.entity.account.Account;

@DomainService
@RequiredArgsConstructor
public class DebitAccountUseCase implements CommandHandler<DebitAccountCommand> {

    private final AccountSpi accountSpi;

    @Override
    public EventList handle(DebitAccountCommand debitAccountCommand) {
        final Account account = accountSpi.findByUserId(debitAccountCommand.userId())
                .orElseThrow(() -> new AccountNotExistsException(debitAccountCommand.userId()))
                .debit(debitAccountCommand.amount());
        accountSpi.save(account);

        return EventList.empty();
    }
}