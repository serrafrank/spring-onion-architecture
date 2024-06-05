package org.pay_my_buddy.application.use_case.account.command.debit_account;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.application.common.DomainService;
import org.pay_my_buddy.application.common.api.CommandHandler;
import org.pay_my_buddy.application.common.api.CommandResponse;
import org.pay_my_buddy.application.use_case.account.command.create_account.AccountNotExistsException;
import org.pay_my_buddy.application.use_case.account.AccountSpi;
import org.pay_my_buddy.entity.account.Account;

@DomainService
@RequiredArgsConstructor
public class DebitAccountUseCase implements CommandHandler<DebitAccountCommand, Void> {

    private final AccountSpi accountSpi;

    @Override
    public CommandResponse<Void> handle(DebitAccountCommand debitAccountCommand) {
        final Account account = accountSpi.findByUserId(debitAccountCommand.userId())
                .orElseThrow(() -> new AccountNotExistsException(debitAccountCommand.userId()))
                .debit(debitAccountCommand.amount());
        accountSpi.save(account);

        return CommandResponse.empty();
    }
}