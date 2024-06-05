package org.pay_my_buddy.application.common.api;

/**
 * This interface defines the contract for an API provider.
 * An API provider is responsible for providing access to the different buses in the system.
 * These buses include the QueryApi, CommandApi, and EventApi.
 */
public interface ApiProvider {

    CommandApi getCommandApi();

    QueryApi getQueryApi();

    /**
     * This method is responsible for dispatching a command to its respective handler.
     *
     * @param command The command to execute.
     * @param <C>     The type of the command.
     */
    default <C extends AbstractCommand> void execute(C command) {
        getCommandApi().execute(command);
    }

    /**
     * This method is responsible for executing a query and returning the result.
     *
     * @param query The query to execute.
     * @param <R>   The type of the result.
     * @param <Q>   The type of the query.
     * @return The result of the query.
     */
    default <R, Q extends Query<R>> R request(Q query) {
        return getQueryApi().request(query);
    }
}