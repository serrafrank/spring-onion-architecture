package org.pay_my_buddy.core.command.infrastructure.bus;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.pay_my_buddy.core.command.domain.bus.CommandProvider;
import org.pay_my_buddy.core.command.domain.bus.CommandRegistry;
import org.pay_my_buddy.core.framework.domain.message.Command;
import org.pay_my_buddy.core.command.application.CommandHandler;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.core.framework.domain.exception.InternalErrorException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

@Component
public class ApplicationCommandRegistry implements CommandRegistry {

    private final Map<Class<? extends Command>, CommandProvider<?>> providerMap = new HashMap<>();

    public ApplicationCommandRegistry(ApplicationContext applicationContext) {
        Stream.of(applicationContext.getBeanNamesForType(CommandHandler.class))
                .forEach(name -> register(applicationContext, name));
    }

    private void register(ApplicationContext applicationContext, String name) {
        final Class<CommandHandler<?>> handlerClass = (Class<CommandHandler<?>>) applicationContext.getType(name);
        final Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass, CommandHandler.class);
        final Class<? extends Command> commandType = (Class<? extends Command>) generics[0];
        if (providerMap.containsKey(commandType)) {
            throw BusinessException.wrap(new InternalErrorException("Duplicate command handler for " + commandType + " found: " + handlerClass + " / added " + providerMap.get(commandType).get()));
        }
        providerMap.put(commandType, new CommandProvider<>(applicationContext, handlerClass));
    }


    @Override
    public <COMMAND extends Command> CommandHandler<COMMAND> get(Class<COMMAND> commandClass) {
        return (CommandHandler<COMMAND>) providerMap.get(commandClass).get();
    }
}
