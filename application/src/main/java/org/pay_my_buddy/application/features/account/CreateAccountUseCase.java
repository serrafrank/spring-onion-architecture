package org.pay_my_buddy.application.features.account;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.application.common.DomainService;
import org.pay_my_buddy.application.common.api.command.CommandHandler;
import org.pay_my_buddy.application.common.api.command.EventList;
import org.pay_my_buddy.application.features.account.api.CreateAccountCommand;
import org.pay_my_buddy.application.features.account.exception.AccountAlreadyExistsException;
import org.pay_my_buddy.application.features.account.spi.AccountSpi;
import org.pay_my_buddy.entity.account.Account;

@DomainService
@RequiredArgsConstructor
public class CreateAccountUseCase implements CommandHandler<CreateAccountCommand> {

    private final AccountSpi accountSpi;


    @Override
    public EventList handle(CreateAccountCommand createAccountCommand) {

        if (accountSpi.existsForUserId(createAccountCommand.userId())) {
            throw new AccountAlreadyExistsException(createAccountCommand.userId());
        }

        Account account = Account.of(createAccountCommand.userId());
        accountSpi.save(account);
        return EventList.empty();
    }
}