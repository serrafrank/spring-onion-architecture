package com.example.paymybuddy.infrastructure.bus;

import com.example.paymybuddy.application.shared.message_handler.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpringQueryBus implements QueryApi {

    private final List<QueryHandler<?, ?>> queryUseCases;

    @Override
    public <T extends Query, R> R ask(T query) {
        return this.queryUseCases.stream()
                .filter(c -> c.matchWith(query.getClass()))
                .map(c -> (QueryHandler<T, R>) c)
                .reduce((a, b) -> {
                    throw new IllegalArgumentException("Multiple query use cases found for " + query.getClass().getName());
                })
                .orElseThrow(() -> new IllegalArgumentException("No query use case found for " + query.getClass().getName()))
                .execute(query);
    }
}
