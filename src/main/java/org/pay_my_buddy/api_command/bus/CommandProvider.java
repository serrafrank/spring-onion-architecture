package org.pay_my_buddy.api_command.bus;

import org.pay_my_buddy.api_command.CommandHandler;
import org.springframework.context.ApplicationContext;

@SuppressWarnings("unchecked")
class CommandProvider<COMMAND_HANDLER extends CommandHandler<?>> {

	private final ApplicationContext applicationContext;
	private final Class<COMMAND_HANDLER> type;

	CommandProvider(ApplicationContext applicationContext, Class<COMMAND_HANDLER> type) {
		this.applicationContext = applicationContext;
		this.type = type;
	}

	public COMMAND_HANDLER get() {
		return applicationContext.getBean(type);
	}
}
