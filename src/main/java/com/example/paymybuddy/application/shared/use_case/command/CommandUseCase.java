package com.example.paymybuddy.application.shared.use_case.command;

public interface CommandUseCase<T extends Command> {

    void execute(T command);

    Class<?> getRequestType();

    default boolean canHandle(Class<? extends Command> type) {
        return getRequestType().isAssignableFrom(type);
    }
}