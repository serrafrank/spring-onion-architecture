package org.pay_my_buddy.application.features.account;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.application.common.DomainService;
import org.pay_my_buddy.application.common.api.command.CommandHandler;
import org.pay_my_buddy.application.common.api.command.EventList;
import org.pay_my_buddy.application.features.account.api.CreditAccountCommand;
import org.pay_my_buddy.application.features.account.exception.AccountNotExistsException;
import org.pay_my_buddy.application.features.account.spi.AccountSpi;
import org.pay_my_buddy.entity.account.Account;

@DomainService
@RequiredArgsConstructor
public class CreditAccountUseCase implements CommandHandler<CreditAccountCommand> {

    private final AccountSpi accountSpi;


    @Override
    public EventList handle(CreditAccountCommand creditAccountCommand) {
        final Account account = accountSpi.findByUserId(creditAccountCommand.userId())
                .orElseThrow(() -> new AccountNotExistsException(creditAccountCommand.userId()))
                .credit(creditAccountCommand.amount());
        accountSpi.save(account);

        return EventList.empty();
    }
}