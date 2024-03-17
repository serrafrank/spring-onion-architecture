package org.pay_my_buddy.entity.transaction.api;

import lombok.NonNull;
import org.pay_my_buddy.entity.account.Amount;
import org.pay_my_buddy.entity.commun.api.command.Command;
import org.pay_my_buddy.entity.commun.entity.Id;

public record CreateTransactionCommand(
        @NonNull
        Id debtorId,
        @NonNull
        Id creditorId,
        @NonNull
        Amount amount) implements Command {

}
