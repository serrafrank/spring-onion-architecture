package com.example.paymybuddy.application.account.use_case.debit_account;

import com.example.paymybuddy.application.account.domain.AccountAggregate;
import com.example.paymybuddy.application.account.spi.AccountRepository;
import com.example.paymybuddy.application.account.use_case.DebitAccountUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DebitAccountUseCaseImpl implements DebitAccountUseCase {
    
    private final AccountRepository accountRepository;

    @Override
    public void execute(DebitAccountCommand debitAccountCommand) {
        final AccountAggregate account = accountRepository.findByUserId(debitAccountCommand.userId())
                .orElseThrow(() -> new RuntimeException("Account not found"))
                .debit(debitAccountCommand.amount());
        accountRepository.save(account);
    }
}