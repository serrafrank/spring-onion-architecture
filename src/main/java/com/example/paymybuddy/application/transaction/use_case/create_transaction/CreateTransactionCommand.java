package com.example.paymybuddy.application.transaction.use_case.create_transaction;

import com.example.paymybuddy.application.Command;
import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.common.value_object.Amount;
import lombok.NonNull;

public record CreateTransactionCommand(
        @NonNull
        Id debtorId,
        @NonNull
        Id creditorId,
        @NonNull
        Amount amount)  implements Command {

}
