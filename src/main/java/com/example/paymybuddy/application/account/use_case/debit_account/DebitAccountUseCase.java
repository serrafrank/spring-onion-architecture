package com.example.paymybuddy.application.account.use_case.debit_account;

import com.example.paymybuddy.application.account.domain.AccountAggregate;
import com.example.paymybuddy.application.account.spi.AccountRepository;
import com.example.paymybuddy.application.configuration.DomainService;
import com.example.paymybuddy.application.shared.message_handler.CommandHandler;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class DebitAccountUseCase implements CommandHandler<DebitAccountCommand> {

    private final AccountRepository accountRepository;

    @Override
    public void execute(DebitAccountCommand debitAccountCommand) {
        final AccountAggregate account = accountRepository.findByUserId(debitAccountCommand.userId())
                .orElseThrow(() -> new RuntimeException("Account not found"))
                .debit(debitAccountCommand.amount());
        accountRepository.save(account);
    }
}