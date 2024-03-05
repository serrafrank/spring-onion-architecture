package com.example.paymybuddy.application.account.use_case.credit_account;

import com.example.paymybuddy.application.shared.use_case.command.Command;
import com.example.paymybuddy.application.shared.entity.id.Id;
import com.example.paymybuddy.application.shared.value_object.Amount;
import lombok.NonNull;

public record CreditAccountCommand(
        @NonNull Id userId,
        @NonNull Amount amount) implements Command {
}
