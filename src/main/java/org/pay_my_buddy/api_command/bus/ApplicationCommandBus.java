package org.pay_my_buddy.api_command.bus;

import org.pay_my_buddy.api_command.Command;
import org.pay_my_buddy.api_command.CommandBus;
import org.pay_my_buddy.api_command.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class ApplicationCommandBus implements CommandBus {

    private final CommandRegistry registry;

    public ApplicationCommandBus(CommandRegistry registry) {
        this.registry = registry;
    }

    @Override
    public <COMMAND extends Command> void execute(COMMAND command) {
        CommandHandler<COMMAND> commandHandler = (CommandHandler<COMMAND>) registry.get(command.getClass());
        commandHandler.handle(command);
    }
}
