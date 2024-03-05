package com.example.paymybuddy.application.account.use_case.debit_account;

import com.example.paymybuddy.application.account.domain.AccountAggregate;
import com.example.paymybuddy.application.account.spi.AccountRepository;
import com.example.paymybuddy.application.shared.use_case.command.AbstractCommandUseCase;
import com.example.paymybuddy.application.configuration.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class DebitAccountUseCase extends AbstractCommandUseCase<DebitAccountCommand>{

    private final AccountRepository accountRepository;

    @Override
    public void execute(DebitAccountCommand debitAccountCommand) {
        final AccountAggregate account = accountRepository.findByUserId(debitAccountCommand.userId())
                .orElseThrow(() -> new RuntimeException("Account not found"))
                .debit(debitAccountCommand.amount());
        accountRepository.save(account);
    }
}