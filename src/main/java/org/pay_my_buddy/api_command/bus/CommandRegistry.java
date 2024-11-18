package org.pay_my_buddy.api_command.bus;

import org.pay_my_buddy.api_command.Command;
import org.pay_my_buddy.api_command.CommandHandler;

public interface CommandRegistry {

    <COMMAND extends Command> CommandHandler<COMMAND> get(Class<COMMAND> commandClass);
}
