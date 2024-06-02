package org.pay_my_buddy.application.account;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.account.Account;
import org.pay_my_buddy.entity.account.api.CreditAccountCommand;
import org.pay_my_buddy.entity.account.spi.AccountSpi;
import org.pay_my_buddy.entity.common.ApplicationService;
import org.pay_my_buddy.entity.common.api.command.CommandHandler;
import org.pay_my_buddy.entity.common.api.command.EventList;

@ApplicationService
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