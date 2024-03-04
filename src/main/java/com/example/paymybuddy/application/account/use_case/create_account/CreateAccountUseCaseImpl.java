package com.example.paymybuddy.application.account.use_case.create_account;

import com.example.paymybuddy.application.account.domain.AccountAggregate;
import com.example.paymybuddy.application.account.spi.AccountRepository;
import com.example.paymybuddy.application.account.use_case.CreateAccountUseCase;
import com.example.paymybuddy.application.account.use_case.credit_account.CreditAccountCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateAccountUseCaseImpl implements CreateAccountUseCase {

    private final AccountRepository accountRepository;


    @Override
    public void execute(CreditAccountCommand creditAccountCommand) {
        if(accountRepository.existsByUserId(creditAccountCommand.userId())) {
            throw new RuntimeException("Account already exists");
        }
        AccountAggregate account = new AccountAggregate(creditAccountCommand.userId());
        accountRepository.save(account);
    }
}