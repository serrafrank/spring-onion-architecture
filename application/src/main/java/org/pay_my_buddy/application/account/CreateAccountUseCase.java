package org.pay_my_buddy.application.account;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.account.*;
import org.pay_my_buddy.entity.commun.api.command.CommandHandler;
import org.pay_my_buddy.entity.commun.ApplicationService;

@ApplicationService
@RequiredArgsConstructor
public class CreateAccountUseCase implements CommandHandler<CreateAccountCommand> {

    private final AccountRepository accountRepository;


    @Override
    public void handle(CreateAccountCommand createAccountCommand) {

        if (accountRepository.existsForUserId(createAccountCommand.userId())) {
            throw new AccountAlreadyExistsException(createAccountCommand.userId());
        }

        Account account = new Account(createAccountCommand.userId());
        accountRepository.save(account);
    }
}