package org.pay_my_buddy.entity.application.account.api;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;
import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.amount.Amount;
import org.pay_my_buddy.entity.common.api.command.AbstractCommand;

@Value
@Accessors(fluent = true)
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
