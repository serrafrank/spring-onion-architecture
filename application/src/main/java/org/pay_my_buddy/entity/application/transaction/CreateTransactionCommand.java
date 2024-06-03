package org.pay_my_buddy.entity.application.transaction;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;
import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.amount.Amount;
import org.pay_my_buddy.entity.common.api.command.AbstractCommand;

import java.util.Objects;

@Value
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class CreateTransactionCommand extends AbstractCommand {
    Id debtorId;
    Id creditorId;
    Amount amount;

    private CreateTransactionCommand(Id debtorId, Id creditorId, Amount amount) {

        if (Objects.isNull(debtorId)) {
            throw new IllegalArgumentException("Debtor id cannot be null");
        }
        if (Objects.isNull(creditorId)) {
            throw new IllegalArgumentException("Creditor id cannot be null");
        }
        if (Objects.isNull(amount)) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        this.debtorId = debtorId;
        this.creditorId = creditorId;
        this.amount = amount;
    }

    public static CreateTransactionCommand of(Id debtorId, Id creditorId, Amount amount) {
        return new CreateTransactionCommand(debtorId, creditorId, amount);
    }


}
