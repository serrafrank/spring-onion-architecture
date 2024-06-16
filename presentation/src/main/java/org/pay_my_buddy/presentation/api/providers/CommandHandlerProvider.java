package org.pay_my_buddy.presentation.api.providers;

import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.application.common.api.Command;
import org.pay_my_buddy.application.common.api.CommandHandler;
import org.pay_my_buddy.application.common.api.DuplicateHandlerFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is a Spring specific implementation of a provider for CommandHandler instances.
 * It is annotated with @Component, meaning that Spring will automatically create an instance of this class and manage it.
 * It is also annotated with @Slf4j, which provides a logger instance.
 * It maintains a map of Command classes to their respective CommandHandler instances.
 */
@Slf4j
@Component
public class CommandHandlerProvider {

    /**
     * The map of Command classes to their respective CommandHandler instances.
     */
    private final Map<Class<? extends Command<?>>, CommandProvider<?>> commandHandlers = new HashMap<>();

    /**
     * Constructor for the CommandHandlerProvider.
     * It takes a list of CommandHandler instances, and maps each Command class to its respective handler.
     *
     * @param applicationContext the application context
     */
    public CommandHandlerProvider(ApplicationContext applicationContext) {
        List.of(applicationContext.getBeanNamesForType(CommandHandler.class))
                .forEach(name -> register(applicationContext, name));

        log.info("CommandHandlerProvider initialized with {} handlers", this.commandHandlers.size());
    }

    /**
     * This method returns the CommandHandler instance for a given Command.
     * If no handler is found, it returns an empty Optional.
     *
     * @param command the command for which to get the handler
     * @return the CommandHandler instance for the given command, or an empty Optional if no handler is found
     */
    @SuppressWarnings("unchecked")
    public <C extends Command<R>, R> Optional<CommandHandler<C, R>> getHandler(C command) {
        if (command == null) {
            throw new NullPointerException("Command cannot be null");
        }

        return Optional.ofNullable(commandHandlers.get(command.getClass()))
                .map(handler -> (CommandHandler<C, R>) handler.get());
    }

    /**
     * This method resolves the Command class for a given CommandHandler.
     * It uses the GenericTypeResolver from Spring to resolve the generic type argument of the CommandHandler.
     *
     */
    private void register(ApplicationContext applicationContext, String name) {
        Class<CommandHandler<?, ?>> handlerClass = (Class<CommandHandler<?, ?>>) applicationContext.getType(name);
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass, CommandHandler.class);
        Class<? extends Command<?>> commandType = (Class<? extends Command<?>>) generics[0];

        if (commandHandlers.containsKey(commandType)) {
            throw new DuplicateHandlerFoundException(commandType.getSimpleName());
        }

        commandHandlers.put(commandType, new CommandProvider<>(applicationContext, handlerClass));
    }

}
