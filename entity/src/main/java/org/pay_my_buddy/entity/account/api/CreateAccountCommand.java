package org.pay_my_buddy.entity.account.api;

import lombok.NonNull;
import org.pay_my_buddy.entity.common.api.command.Command;
import org.pay_my_buddy.entity.common.entity.Id;

public record CreateAccountCommand(@NonNull Id userId) implements Command {
}
