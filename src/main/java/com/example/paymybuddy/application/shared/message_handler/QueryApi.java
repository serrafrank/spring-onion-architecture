package com.example.paymybuddy.application.shared.message_handler;

import java.util.List;

public interface QueryApi {

    <T extends Query, R> List<QueryHandler<T, R>> getHandlers();

    default <T extends Query, R> R ask(T query) {
        return this.getHandlers().stream()
                .filter(c -> c.matchWith(query.getClass()))
                .map(c -> (QueryHandler<T, R>) c)
                .reduce((a, b) -> {
                    throw new IllegalArgumentException("Multiple query use cases found for " + query.getClass().getName());
                })
                .orElseThrow(() -> new IllegalArgumentException("No query use case found for " + query.getClass().getName()))
                .execute(query);
    }
}
