package org.pay_my_buddy.modules.money_account.shared.command;

import org.pay_my_buddy.core.framework.domain.message.Command;
import org.pay_my_buddy.core.framework.domain.validator.Validate;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;

import java.util.Currency;


public record CreateMoneyAccountCommand(EntityId userId, Currency currency) implements Command {

    public CreateMoneyAccountCommand {
        Validate.checkIf(userId).isNotNull();
        Validate.checkIf(currency).isNotNull();
    }
}
