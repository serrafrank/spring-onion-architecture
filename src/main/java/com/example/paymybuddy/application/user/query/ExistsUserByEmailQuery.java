package com.example.paymybuddy.application.user.query;

import com.example.paymybuddy.application.shared.message_handler.Query;
import jakarta.validation.constraints.NotBlank;

public record ExistsUserByEmailQuery(@NotBlank String email) implements Query<Boolean> {
}
