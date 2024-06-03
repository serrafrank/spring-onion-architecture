package org.pay_my_buddy.presentation.api.providers;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.entity.common.api.WrongHandlerImplementationException;
import org.pay_my_buddy.entity.common.api.query.Query;
import org.pay_my_buddy.entity.common.api.query.QueryHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class is a Spring specific implementation of a provider for QueryHandler instances.
 * It is annotated with @Component, meaning that Spring will automatically create an instance of this class and manage it.
 * It is also annotated with @Slf4j, which provides a logger instance.
 * It maintains a map of Query classes to their respective QueryHandler instances.
 */
@Slf4j
@Getter
@Component
public class QueryHandlerProvider {

    /**
     * The map of Query classes to their respective QueryHandler instances.
     */
    private final Map<Class<? extends Query<?>>, QueryHandler<? extends Query<?>, ?>> queryHandlers;

    /**
     * Constructor for the QueryHandlerProvider.
     * It takes a list of QueryHandler instances, and maps each Query class to its respective handler.
     *
     * @param handlers the list of QueryHandler instances
     */
    public QueryHandlerProvider(ApplicationContext applicationContext) {
        queryHandlers = applicationContext.getBeansOfType(QueryHandler.class)
                .values()
                .stream()
                .map(handler -> (QueryHandler<?, ?>) handler)
                .map(handler -> Map.entry(resolve(handler), handler))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        log.info("QueryHandlerProvider initialized with {} handlers", queryHandlers.size());
    }

    /**
     * This method returns the QueryHandler instance for a given Query.
     * If no handler is found, it returns an empty Optional.
     *
     * @param query the query for which to get the handler
     * @return the QueryHandler instance for the given query, or an empty Optional if no handler is found
     */
    @SuppressWarnings("unchecked")
    public <Q extends Query<R>, R> Optional<QueryHandler<Q, R>> getHandler(Q query) {
        if (query == null) {
            throw new NullPointerException("Query cannot be null");
        }
        return Optional.ofNullable(queryHandlers.get(query.getClass()))
                .map(handler -> (QueryHandler<Q, R>) handler);
    }

    /**
     * This method resolves the Query class for a given QueryHandler.
     * It uses the GenericTypeResolver from Spring to resolve the generic type argument of the QueryHandler.
     * If the generic type argument cannot be resolved, it throws a WrongHandlerImplementationException.
     *
     * @param handler the handler for which to resolve the Query class
     * @return the Query class for the given handler
     * @throws WrongHandlerImplementationException if the generic type argument of the QueryHandler cannot be resolved
     */
    @SuppressWarnings("unchecked")
    private <Q extends Query<R>, R> Class<Q> resolve(QueryHandler<Q, R> handler) {
        Class<?>[] parameters = GenericTypeResolver.resolveTypeArguments(handler.getClass(), QueryHandler.class);
        if (parameters == null) {
            throw new WrongHandlerImplementationException(handler);
        }
        return (Class<Q>) parameters[0];
    }
}