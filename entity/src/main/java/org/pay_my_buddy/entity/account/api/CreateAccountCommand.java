package org.pay_my_buddy.entity.account.api;

import lombok.NonNull;
import org.pay_my_buddy.entity.commun.api.command.Command;
import org.pay_my_buddy.entity.commun.entity.Id;

public record CreateAccountCommand(@NonNull Id userId) implements Command {
}
