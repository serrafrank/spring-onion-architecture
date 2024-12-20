package org.pay_my_buddy.modules.money_account.shared.command;

import org.pay_my_buddy.core.framework.domain.message.Command;
import org.pay_my_buddy.core.framework.domain.validator.Validate;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;
import org.pay_my_buddy.modules.money_account.shared.MoneyAccountId;

import java.math.BigDecimal;
import java.util.Currency;


public record DebitMoneyAccountCommand(MoneyAccountId id, BigDecimal amount, Currency currency) implements Command {

    public DebitMoneyAccountCommand {
        Validate.checkIf(id).isNotNull();
        Validate.checkIf(amount).isNotNull().isPositive("Amount must be > 0");
        Validate.checkIf(currency).isNotNull();
    }
}
