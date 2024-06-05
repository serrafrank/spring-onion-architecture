package org.pay_my_buddy.application.use_case.account.command.credit_account;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.pay_my_buddy.application.common.api.AbstractCommand;
import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.amount.Amount;

@Value
@EqualsAndHashCode(callSuper = true)
public class CreditAccountCommand extends AbstractCommand<Void> {
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
