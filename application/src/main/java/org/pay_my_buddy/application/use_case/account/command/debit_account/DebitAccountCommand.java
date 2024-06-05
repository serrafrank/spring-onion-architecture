package org.pay_my_buddy.application.use_case.account.command.debit_account;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.pay_my_buddy.application.common.api.AbstractCommand;
import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.amount.Amount;

@Value
@EqualsAndHashCode(callSuper = true)
public class DebitAccountCommand extends AbstractCommand {
    Id userId;
    Amount amount;

    private DebitAccountCommand(Id userId, Amount amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public static DebitAccountCommand of(Id userId, Amount amount) {
        return new DebitAccountCommand(userId, amount);
    }

}
