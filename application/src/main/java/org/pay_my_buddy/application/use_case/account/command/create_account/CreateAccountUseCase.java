package org.pay_my_buddy.application.use_case.account.command.create_account;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.application.common.DomainService;
import org.pay_my_buddy.application.common.api.CommandHandler;
import org.pay_my_buddy.application.common.api.CommandResponse;
import org.pay_my_buddy.application.use_case.account.AccountSpi;
import org.pay_my_buddy.entity.account.Account;

@DomainService
@RequiredArgsConstructor
public class CreateAccountUseCase implements CommandHandler<CreateAccountCommand, Void> {

    private final AccountSpi accountSpi;


    @Override
    public CommandResponse<Void> handle(CreateAccountCommand createAccountCommand) {

        if (accountSpi.existsForUserId(createAccountCommand.userId())) {
            throw new AccountAlreadyExistsException(createAccountCommand.userId());
        }

        Account account = Account.of(createAccountCommand.userId());
        accountSpi.save(account);
        return CommandResponse.empty();
    }
}