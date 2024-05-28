package org.pay_my_buddy.entity.account.api;

import lombok.NonNull;
import org.pay_my_buddy.entity.common.api.command.Command;
import org.pay_my_buddy.entity.common.entity.Id;
import org.pay_my_buddy.entity.common.value_object.Amount;

public record CreditAccountCommand(
        @NonNull Id userId,
        @NonNull Amount amount) implements Command {
}
