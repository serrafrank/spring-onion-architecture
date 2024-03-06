package com.example.paymybuddy.application.account.use_case.create_account;

import com.example.paymybuddy.application.account.domain.AccountAggregate;
import com.example.paymybuddy.application.account.spi.AccountRepository;
import com.example.paymybuddy.application.account.use_case.credit_account.CreditAccountCommand;
import com.example.paymybuddy.application.configuration.DomainService;
import com.example.paymybuddy.application.shared.message_handler.CommandHandler;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class CreateAccountUseCase implements CommandHandler<CreditAccountCommand> {

    private final AccountRepository accountRepository;


    @Override
    public void execute(CreditAccountCommand creditAccountCommand) {
        if (accountRepository.existsByUserId(creditAccountCommand.userId())) {
            throw new RuntimeException("Account already exists");
        }
        AccountAggregate account = new AccountAggregate(creditAccountCommand.userId());
        accountRepository.save(account);
    }
}