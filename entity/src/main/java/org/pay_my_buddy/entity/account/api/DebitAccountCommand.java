package org.pay_my_buddy.entity.account.api;

import lombok.NonNull;
import org.pay_my_buddy.entity.account.Amount;
import org.pay_my_buddy.entity.commun.api.command.Command;
import org.pay_my_buddy.entity.commun.entity.Id;

public record DebitAccountCommand(
        @NonNull Id userId,
        @NonNull Amount amount) implements Command {

}
