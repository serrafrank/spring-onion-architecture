package org.pay_my_buddy.entity.application.account.api;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;
import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.common.api.command.AbstractCommand;

import java.util.Objects;

@Value
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class CreateAccountCommand extends AbstractCommand {
    Id userId;

    private CreateAccountCommand(Id userId) {
        if (Objects.isNull(userId)) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        this.userId = userId;
    }

    public static CreateAccountCommand of(Id userId) {
        return new CreateAccountCommand(userId);
    }
}
