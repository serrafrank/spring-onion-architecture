package org.pay_my_buddy.modules.money_account.shared.command;

public interface MoneyAccountGateway {
    void handle(CreateMoneyAccountCommand createMoneyAccountCommand);

    void handle(CreditMoneyAccountCommand command);

    void handle(DebitMoneyAccountCommand command);
}
