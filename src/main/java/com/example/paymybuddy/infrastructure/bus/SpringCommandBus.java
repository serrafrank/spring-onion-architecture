package com.example.paymybuddy.infrastructure.bus;

import com.example.paymybuddy.application.CommandBus;
import com.example.paymybuddy.application.shared.use_case.command.Command;
import com.example.paymybuddy.application.shared.use_case.command.CommandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SpringCommandBus implements CommandBus {

    private final List<CommandUseCase<?>> commandUseCases;


    public <T extends Command> void dispatch(T command) {
        this.commandUseCases.stream()
                .filter(c -> c.canHandle(command.getClass()))
                .map(c -> (CommandUseCase<T>) c)
                .reduce((a, b) -> {
                    throw new IllegalArgumentException("Multiple command use cases found for " + command.getClass().getName());
                })
                .orElseThrow(() -> new IllegalArgumentException("No command use case found for " + command.getClass().getName()))
                .execute(command);
    }
}
