package com.example.paymybuddy.application.user.query;

import com.example.paymybuddy.application.shared.use_case.query.Query;
import jakarta.validation.constraints.NotBlank;

public record ExistsUserByEmailQuery(@NotBlank String email) implements Query<Boolean> {
}
