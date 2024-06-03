package org.pay_my_buddy.entity.application.account;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.common.DomainService;
import org.pay_my_buddy.entity.account.Account;
import org.pay_my_buddy.entity.application.account.api.DebitAccountCommand;
import org.pay_my_buddy.entity.application.account.exception.AccountNotExistsException;
import org.pay_my_buddy.entity.application.account.spi.AccountSpi;
import org.pay_my_buddy.entity.common.api.command.CommandHandler;
import org.pay_my_buddy.entity.common.api.command.EventList;

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