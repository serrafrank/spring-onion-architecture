package com.example.paymybuddy.application.account.use_case.create_account;

import com.example.paymybuddy.application.shared.use_case.command.Command;
import com.example.paymybuddy.application.shared.entity.id.Id;
import lombok.NonNull;

public record CreateAccountCommand(
        @NonNull Id userId) implements Command {
}
