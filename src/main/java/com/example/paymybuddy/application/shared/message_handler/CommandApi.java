package com.example.paymybuddy.application.shared.message_handler;

import java.util.List;

public interface CommandApi {

    List<CommandHandler<?>> getHandlers();

    default <T extends Command> void dispatch(T command) {
        this.getHandlers().stream()
                .filter(c -> c.matchWith(command.getClass()))
                .map(c -> (CommandHandler<T>) c)
                .reduce((a, b) -> {
                    throw new IllegalArgumentException("Multiple command use cases found for " + command.getClass().getName());
                })
                .ifPresent(c -> c.execute(command));
    }
}
