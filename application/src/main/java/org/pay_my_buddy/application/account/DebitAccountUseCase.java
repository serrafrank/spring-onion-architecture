package org.pay_my_buddy.application.account;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.account.Account;
import org.pay_my_buddy.entity.account.AccountRepository;
import org.pay_my_buddy.entity.account.DebitAccountCommand;
import org.pay_my_buddy.entity.commun.api.command.CommandHandler;
import org.pay_my_buddy.entity.commun.ApplicationService;

@ApplicationService
@RequiredArgsConstructor
public class DebitAccountUseCase implements CommandHandler<DebitAccountCommand> {

    private final AccountRepository accountRepository;

    @Override
    public void handle(DebitAccountCommand debitAccountCommand) {
        final Account account = accountRepository.findByUserId(debitAccountCommand.userId())
                .orElseThrow(() -> new RuntimeException("Account not found"))
                .debit(debitAccountCommand.amount());
        accountRepository.save(account);
    }
}