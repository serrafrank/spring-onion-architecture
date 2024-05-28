package org.pay_my_buddy.application.account;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.account.Account;
import org.pay_my_buddy.entity.account.api.DebitAccountCommand;
import org.pay_my_buddy.entity.account.spi.AccountSpi;
import org.pay_my_buddy.entity.common.ApplicationService;
import org.pay_my_buddy.entity.common.api.command.CommandHandler;

@ApplicationService
@RequiredArgsConstructor
public class DebitAccountUseCase implements CommandHandler<DebitAccountCommand> {

    private final AccountSpi accountSpi;

    @Override
    public void handle(DebitAccountCommand debitAccountCommand) {
        final Account account = accountSpi.findByUserId(debitAccountCommand.userId())
                .orElseThrow(() -> new AccountNotExistsException(debitAccountCommand.userId()))
                .debit(debitAccountCommand.amount());
        accountSpi.save(account);
    }
}