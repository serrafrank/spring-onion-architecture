package org.pay_my_buddy.core.command.application;

import org.pay_my_buddy.core.framework.domain.message.Command;

@FunctionalInterface
public interface CommandHandler<COMMAND extends Command> {
    void handle(COMMAND command);

}
