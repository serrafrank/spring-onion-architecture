package org.pay_my_buddy.shared.command.application;

import org.pay_my_buddy.shared.command.domain.CommandHandler;
import org.pay_my_buddy.shared.command.domain.CommandRegistry;
import org.pay_my_buddy.shared.common.domain.api.command.Command;
import org.pay_my_buddy.shared.common.domain.api.command.CommandBus;
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
