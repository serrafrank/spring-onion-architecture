package org.pay_my_buddy.presentation.api.providers;

import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.entity.commun.api.DuplicateHandlerFoundException;
import org.pay_my_buddy.entity.commun.api.command.Command;
import org.pay_my_buddy.entity.commun.api.command.CommandHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private final Map<Class<? extends Command>, CommandHandler<? extends Command>> commandHandlers;

    /**
     * Constructor for the CommandHandlerProvider.
     * It takes a list of CommandHandler instances, and maps each Command class to its respective handler.
     *
     * @param applicationContext  the application context
     */
    public CommandHandlerProvider(ApplicationContext applicationContext) {
        List<? extends CommandHandler<?>> handlers = applicationContext.getBeansOfType(CommandHandler.class)
                .values()
                .stream()
                .map(handler -> (CommandHandler<?>) handler)
                .toList();
        try {

            this.commandHandlers = handlers
                    .stream()
                    .map(handler -> Map.entry(resolve(handler), handler))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        } catch (IllegalStateException e) {
            var duplicateHandlers = handlers
                    .stream()
                    .map(handler -> resolve(handler).getName())
                    .collect(Collectors.toSet());
            throw new DuplicateHandlerFoundException(duplicateHandlers);
        }

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
    public <C extends Command> Optional<CommandHandler<C>> getHandler(C command) {
        if (command == null) {
            throw new NullPointerException("Command cannot be null");
        }
        return Optional.ofNullable(commandHandlers.get(command.getClass()))
                .map(handler -> (CommandHandler<C>) handler);
    }

    /**
     * This method resolves the Command class for a given CommandHandler.
     * It uses the GenericTypeResolver from Spring to resolve the generic type argument of the CommandHandler.
     *
     * @param handler the handler for which to resolve the Command class
     * @return the Command class for the given handler
     */
    @SuppressWarnings("unchecked")
    private <C extends Command> Class<C> resolve(CommandHandler<C> handler) {
        return (Class<C>) GenericTypeResolver.resolveTypeArgument(handler.getClass(), CommandHandler.class);
    }

}