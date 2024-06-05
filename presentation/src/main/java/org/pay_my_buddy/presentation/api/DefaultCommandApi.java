package org.pay_my_buddy.presentation.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.application.common.api.NoHandlerFoundException;
import org.pay_my_buddy.application.common.api.Command;
import org.pay_my_buddy.application.common.api.CommandApi;
import org.pay_my_buddy.application.common.api.CommandResponse;
import org.pay_my_buddy.application.common.api.EventApi;
import org.pay_my_buddy.presentation.api.providers.CommandHandlerProvider;
import org.springframework.stereotype.Component;

/**
 * This class is a Spring specific implementation of the CommandApi interface.
 * It is annotated with @Component, meaning that Spring will automatically create an instance of this class and manage it.
 * It uses Lombok's @RequiredArgsConstructor to automatically generate a constructor that initializes final fields.
 * The final fields are automatically injected by Spring due to the @RequiredArgsConstructor annotation.
 * It is also annotated with @Slf4j, which provides a logger instance.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultCommandApi implements CommandApi {

    private final EventApi eventApi;

    /**
     * The CommandHandlerProvider instance. This is automatically injected by Spring.
     */
    private final CommandHandlerProvider commandHandlerProvider;

    /**
     * This method is an implementation of the execute method defined in the CommandApi interface.
     * It takes a command as input and executes it using the appropriate handler.
     * It uses the CommandHandlerProvider to get the appropriate handler for the command.
     * If no handler is found, it throws a NoHandlerFoundException.
     * <p>
     * The handler is then used to handle the command, which returns a list of events.
     * The events are then published using the EventApi.
     *
     * @param command the command to be executed
     * @throws NoHandlerFoundException if no handler is found for the command
     */
    @Override
    public <C extends Command<R>, R> R execute(C command) {
        final var handler = commandHandlerProvider.getHandler(command)
                .orElseThrow(() -> new NoHandlerFoundException(command.getClass()));

        final CommandResponse<?> commandResponse = handler.handle(command);

        publishEvents(commandResponse);

        return (R) commandResponse.response().orElse(null);
    }

    private void publishEvents(CommandResponse<?> events) {
        events.events().forEach(eventApi::publish);
    }

}