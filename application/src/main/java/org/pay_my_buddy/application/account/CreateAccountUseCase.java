package org.pay_my_buddy.application.account;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.account.*;
import org.pay_my_buddy.entity.account.api.CreateAccountCommand;
import org.pay_my_buddy.entity.account.spi.AccountSpi;
import org.pay_my_buddy.entity.commun.api.command.CommandHandler;
import org.pay_my_buddy.entity.commun.ApplicationService;

@ApplicationService
@RequiredArgsConstructor
public class CreateAccountUseCase implements CommandHandler<CreateAccountCommand> {

    private final AccountSpi accountSpi;


    @Override
    public void handle(CreateAccountCommand createAccountCommand) {

        if (accountSpi.existsForUserId(createAccountCommand.userId())) {
            throw new AccountAlreadyExistsException(createAccountCommand.userId());
        }

        Account account = new Account(createAccountCommand.userId());
        accountSpi.save(account);
    }
}