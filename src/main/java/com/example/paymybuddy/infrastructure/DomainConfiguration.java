package com.example.paymybuddy.infrastructure;

import com.example.paymybuddy.application.configuration.DomainService;
import com.example.paymybuddy.application.shared.message_handler.CommandHandler;
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
}