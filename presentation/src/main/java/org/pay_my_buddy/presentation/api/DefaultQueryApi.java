package org.pay_my_buddy.presentation.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.application.common.api.NoHandlerFoundException;
import org.pay_my_buddy.application.common.api.Query;
import org.pay_my_buddy.application.common.api.QueryApi;
import org.pay_my_buddy.presentation.api.providers.QueryHandlerProvider;
import org.springframework.stereotype.Component;

/**
 * This class is a Spring specific implementation of the QueryApi interface.
 * It is annotated with @Component, meaning that Spring will automatically create an instance of this class and manage it.
 * It uses Lombok's @RequiredArgsConstructor to automatically generate a constructor that initializes final fields.
 * The final fields are automatically injected by Spring due to the @RequiredArgsConstructor annotation.
 * It is also annotated with @Slf4j, which provides a logger instance.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultQueryApi implements QueryApi {

    /**
     * The QueryHandlerProvider instance. This is automatically injected by Spring.
     */
    private final QueryHandlerProvider queryHandlerProvider;

    /**
     * This method is an implementation of the request method defined in the QueryApi interface.
     * It takes a query as input and returns the result of the query.
     * It uses the QueryHandlerProvider to get the appropriate handler for the query.
     * If no handler is found, it throws a NoHandlerFoundException.
     *
     * @param query the query to be handled
     * @return the result of the query
     * @throws NoHandlerFoundException if no handler is found for the query
     */
    @Override
    public <R, Q extends Query<R>> R request(Q query) {
        return queryHandlerProvider.getHandler(query)
                .orElseThrow(() -> new NoHandlerFoundException(query.getClass()))
                .handle(query);
    }

}