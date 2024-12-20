package org.pay_my_buddy.modules.money_account.command.application;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.framework.domain.DomainService;
import org.pay_my_buddy.modules.money_account.shared.command.CreateMoneyAccountCommand;
import org.pay_my_buddy.modules.money_account.shared.command.CreditMoneyAccountCommand;
import org.pay_my_buddy.modules.money_account.shared.command.DebitMoneyAccountCommand;
import org.pay_my_buddy.modules.money_account.shared.command.MoneyAccountGateway;


@DomainService
@RequiredArgsConstructor
public class MoneyAccountGatewayImpl implements MoneyAccountGateway {

    private final CreateMoneyAccountUseCase createMoneyAccountUseCase;
    private final  CreditMoneyAccountUseCase creditMoneyAccountUseCase;
    private final         DebitMoneyAccountUseCase debitMoneyAccountUseCase;

    @Override
    public void handle(CreateMoneyAccountCommand command) {
        createMoneyAccountUseCase.handle(command);
    }
    @Override
    public void handle(CreditMoneyAccountCommand command) {
        creditMoneyAccountUseCase.handle(command);
    }
    @Override
    public void handle(DebitMoneyAccountCommand command) {
        debitMoneyAccountUseCase.handle(command);
    }

}
