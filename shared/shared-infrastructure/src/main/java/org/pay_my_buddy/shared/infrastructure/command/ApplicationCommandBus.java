package org.pay_my_buddy.shared.infrastructure.command;

import org.pay_my_buddy.shared.domain.api.command.Command;
import org.pay_my_buddy.shared.domain.api.command.CommandBus;
import org.pay_my_buddy.shared.domain.api.command.CommandHandler;
import org.pay_my_buddy.shared.domain.api.command.CommandRegistry;
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