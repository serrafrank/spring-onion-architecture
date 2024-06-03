package org.pay_my_buddy.entity.application.account;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.common.DomainService;
import org.pay_my_buddy.entity.account.Account;
import org.pay_my_buddy.entity.application.account.api.CreateAccountCommand;
import org.pay_my_buddy.entity.application.account.exception.AccountAlreadyExistsException;
import org.pay_my_buddy.entity.application.account.spi.AccountSpi;
import org.pay_my_buddy.entity.common.api.command.CommandHandler;
import org.pay_my_buddy.entity.common.api.command.EventList;

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