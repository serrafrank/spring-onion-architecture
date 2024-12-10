package org.pay_my_buddy.core.command.domain.bus;

import org.pay_my_buddy.core.framework.domain.message.Command;
import org.pay_my_buddy.core.command.application.CommandHandler;

public interface CommandRegistry {

    <COMMAND extends Command> CommandHandler<COMMAND> get(Class<COMMAND> commandClass);
}
