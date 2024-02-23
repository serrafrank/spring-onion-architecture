package com.example.paymybuddy.application.user.command;

import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.common.valueobject.Amount;
import com.example.paymybuddy.core.user.valueobject.UserId;
import lombok.NonNull;

public record DebitUserCommand(
        @NonNull UserId id,
        @NonNull Amount amount) {

    public DebitUserCommand(Id id, Amount amount) {
        this(new UserId(id), amount);
    }
}
