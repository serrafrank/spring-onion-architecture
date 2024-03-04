package com.example.paymybuddy.application.account.use_case.debit_account;

import com.example.paymybuddy.application.Command;
import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.common.value_object.Amount;
import lombok.NonNull;

public record DebitAccountCommand(
        @NonNull Id userId,
        @NonNull Amount amount)  implements Command {

}
