package com.example.paymybuddy.application.user.query;

import jakarta.validation.constraints.NotBlank;

public record ExistsUserByEmailQuery(@NotBlank String email) {
}
