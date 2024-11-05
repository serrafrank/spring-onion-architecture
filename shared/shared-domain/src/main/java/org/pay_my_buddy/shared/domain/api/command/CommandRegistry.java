package org.pay_my_buddy.shared.domain.api.command;

public interface CommandRegistry {

    <COMMAND extends Command> CommandHandler<COMMAND> get(Class<COMMAND> commandClass);
}
