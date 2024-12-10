package org.pay_my_buddy.core.command.infrastructure.bus;

import org.pay_my_buddy.core.command.domain.bus.CommandRegistry;
import org.pay_my_buddy.core.framework.domain.message.Command;
import org.pay_my_buddy.core.framework.application.CommandBus;
import org.pay_my_buddy.core.command.application.CommandHandler;
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
