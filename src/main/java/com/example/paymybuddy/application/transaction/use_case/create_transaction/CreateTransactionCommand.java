package com.example.paymybuddy.application.transaction.use_case.create_transaction;

import com.example.paymybuddy.application.shared.use_case.command.Command;
import com.example.paymybuddy.application.shared.entity.id.Id;
import com.example.paymybuddy.application.shared.value_object.Amount;
import lombok.NonNull;

public record CreateTransactionCommand(
        @NonNull
        Id debtorId,
        @NonNull
        Id creditorId,
        @NonNull
        Amount amount)  implements Command {

}
