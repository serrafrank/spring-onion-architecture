package org.pay_my_buddy.shared.command.domain;

import org.pay_my_buddy.shared.common.domain.api.command.Command;

@FunctionalInterface
public interface CommandHandler<COMMAND extends Command> {
    void handle(COMMAND command);

}
