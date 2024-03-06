package com.example.paymybuddy.application.account.use_case.debit_account;

import com.example.paymybuddy.application.shared.entity.id.Id;
import com.example.paymybuddy.application.shared.message_handler.Command;
import com.example.paymybuddy.application.shared.value_object.Amount;
import lombok.NonNull;

public record DebitAccountCommand(
        @NonNull Id userId,
        @NonNull Amount amount) implements Command {

}
