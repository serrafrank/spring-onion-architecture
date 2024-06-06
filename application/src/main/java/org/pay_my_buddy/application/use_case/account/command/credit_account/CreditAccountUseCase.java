package org.pay_my_buddy.application.use_case.account.command.credit_account;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.application.common.DomainService;
import org.pay_my_buddy.application.common.api.CommandHandler;
import org.pay_my_buddy.application.common.api.CommandResponse;
import org.pay_my_buddy.application.use_case.account.AccountSpi;
import org.pay_my_buddy.application.use_case.account.command.create_account.AccountNotExistsException;
import org.pay_my_buddy.entity.account.Account;

@DomainService
@RequiredArgsConstructor
public class CreditAccountUseCase implements CommandHandler<CreditAccountCommand, Void> {

    private final AccountSpi accountSpi;


    @Override
    public CommandResponse<Void> handle(CreditAccountCommand creditAccountCommand) {
        final Account account = accountSpi.findByUserId(creditAccountCommand.userId())
                .orElseThrow(() -> new AccountNotExistsException(creditAccountCommand.userId()))
                .credit(creditAccountCommand.amount());
        accountSpi.save(account);

        return CommandResponse.empty();
    }
}