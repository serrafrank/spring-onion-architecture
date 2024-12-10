package org.pay_my_buddy.core.command.domain.bus;

import org.pay_my_buddy.core.command.application.CommandHandler;
import org.springframework.context.ApplicationContext;

@SuppressWarnings("unchecked")
public class CommandProvider<COMMAND_HANDLER extends CommandHandler<?>> {

    private final ApplicationContext applicationContext;
    private final Class<COMMAND_HANDLER> type;

    public CommandProvider(ApplicationContext applicationContext, Class<COMMAND_HANDLER> type) {
        this.applicationContext = applicationContext;
        this.type = type;
    }

    public COMMAND_HANDLER get() {
        return applicationContext.getBean(type);
    }
}
