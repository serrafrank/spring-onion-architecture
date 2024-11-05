package org.pay_my_buddy.shared.infrastructure.command;

import org.pay_my_buddy.shared.domain.api.command.CommandHandler;
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