package com.example.paymybuddy.infrastructure;

import com.example.paymybuddy.application.shared.use_case.command.CommandUseCase;
import com.example.paymybuddy.application.configuration.DomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

@Configuration
@ComponentScan(
        basePackages = "com.example.paymybuddy.application",
        includeFilters = @ComponentScan.Filter(
                type = ANNOTATION,
                classes = {DomainService.class}
        )
)
@Slf4j
public class DomainConfiguration {

        private final List<CommandUseCase<?>> commandUseCases;

        public DomainConfiguration(List<CommandUseCase<?>> commandUseCases) {
                this.commandUseCases = commandUseCases;

                log.info("DomainConfiguration initialized with {} command use cases", commandUseCases.size());

                commandUseCases.forEach(c -> log.info("CommandUseCase: {} -> {}",c.getRequestType().getTypeName(), c.getClass().getSimpleName()));
        }

}
