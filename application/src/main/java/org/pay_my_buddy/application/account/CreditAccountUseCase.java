package org.pay_my_buddy.application.account;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.account.Account;
import org.pay_my_buddy.entity.account.AccountRepository;
import org.pay_my_buddy.entity.account.CreditAccountCommand;
import org.pay_my_buddy.entity.commun.api.command.CommandHandler;
import org.pay_my_buddy.entity.commun.ApplicationService;

@ApplicationService
@RequiredArgsConstructor
public class CreditAccountUseCase implements CommandHandler<CreditAccountCommand> {

    private final AccountRepository accountRepository;


    @Override
    public void handle(CreditAccountCommand creditAccountCommand) {
        final Account account = accountRepository.findByUserId(creditAccountCommand.userId())
                .orElseThrow(() -> new RuntimeException("Account not found"))
                .credit(creditAccountCommand.amount());
        accountRepository.save(account);
    }
}