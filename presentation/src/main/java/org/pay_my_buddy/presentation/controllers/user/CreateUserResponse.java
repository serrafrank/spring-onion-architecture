package org.pay_my_buddy.presentation.controllers.user;

import lombok.Value;
import org.pay_my_buddy.entity.user.UserId;

import java.util.UUID;

public record CreateUserResponse(UUID userId) {
    public static CreateUserResponse of(UserId userId) {
        return new CreateUserResponse(userId.value());
    }
}
