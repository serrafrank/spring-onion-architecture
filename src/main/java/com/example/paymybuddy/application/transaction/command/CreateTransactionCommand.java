package com.example.paymybuddy.application.transaction.command;

import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.common.valueobject.Amount;
import lombok.NonNull;

public record CreateTransactionCommand(
        @NonNull
        Id debtorId,
        @NonNull
        Id creditorId,
        @NonNull
        Amount amount) {

}
