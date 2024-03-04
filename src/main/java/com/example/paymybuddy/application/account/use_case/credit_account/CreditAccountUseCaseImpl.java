package com.example.paymybuddy.application.account.use_case.credit_account;

import com.example.paymybuddy.application.account.domain.AccountAggregate;
import com.example.paymybuddy.application.account.spi.AccountRepository;
import com.example.paymybuddy.application.account.use_case.CreditAccountUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreditAccountUseCaseImpl implements CreditAccountUseCase {

    private final AccountRepository accountRepository;


    @Override
    public void execute(CreditAccountCommand creditAccountCommand) {
        final AccountAggregate account = accountRepository.findByUserId(creditAccountCommand.userId())
                .orElseThrow(() -> new RuntimeException("Account not found"))
                .credit(creditAccountCommand.amount());
        accountRepository.save(account);
    }
}