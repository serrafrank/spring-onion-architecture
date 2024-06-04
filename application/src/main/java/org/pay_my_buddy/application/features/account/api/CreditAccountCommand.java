package org.pay_my_buddy.application.features.account.api;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.pay_my_buddy.application.common.api.command.AbstractCommand;
import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.amount.Amount;

@Value
@EqualsAndHashCode(callSuper = true)
public class CreditAccountCommand extends AbstractCommand {
    Id userId;
    Amount amount;

    private CreditAccountCommand(Id userId, Amount amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public static CreditAccountCommand of(Id userId, Amount amount) {
        return new CreditAccountCommand(userId, amount);
    }


}
