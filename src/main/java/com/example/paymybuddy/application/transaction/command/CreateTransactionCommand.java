package com.example.paymybuddy.application.transaction.command;

import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.common.valueobject.Amount;
import com.example.paymybuddy.core.validator.Constraint;
import lombok.NonNull;
import lombok.Value;

public record CreateTransactionCommand(
        Id debtorId,
        Id creditorId,
        Amount amount) {

    public CreateTransactionCommand {
        Constraint.of(debtorId).notNull().validate();
        Constraint.of(creditorId).notNull().validate();
        Constraint.of(amount).notNull().validate();
    }

}
