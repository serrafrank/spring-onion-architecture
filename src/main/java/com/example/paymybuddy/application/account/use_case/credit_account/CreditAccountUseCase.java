package com.example.paymybuddy.application.account.use_case.credit_account;

import com.example.paymybuddy.application.account.domain.AccountAggregate;
import com.example.paymybuddy.application.account.spi.AccountRepository;
import com.example.paymybuddy.application.configuration.DomainService;
import com.example.paymybuddy.application.shared.message_handler.CommandHandler;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class CreditAccountUseCase implements CommandHandler<CreditAccountCommand> {

    private final AccountRepository accountRepository;


    @Override
    public void execute(CreditAccountCommand creditAccountCommand) {
        final AccountAggregate account = accountRepository.findByUserId(creditAccountCommand.userId())
                .orElseThrow(() -> new RuntimeException("Account not found"))
                .credit(creditAccountCommand.amount());
        accountRepository.save(account);
    }
}