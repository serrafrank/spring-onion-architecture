package org.pay_my_buddy.application.use_case.account.command.create_account;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.pay_my_buddy.application.common.api.AbstractCommand;
import org.pay_my_buddy.entity.Id;

import java.util.Objects;

@Value
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
