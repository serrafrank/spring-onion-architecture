package org.pay_my_buddy.shared.command.domain;

import org.pay_my_buddy.shared.common.domain.api.command.Command;

public interface CommandRegistry {

    <COMMAND extends Command> CommandHandler<COMMAND> get(Class<COMMAND> commandClass);
}
