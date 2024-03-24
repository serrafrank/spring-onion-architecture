package org.pay_my_buddy.entity.commun.api;

import org.pay_my_buddy.entity.commun.api.command.Command;
import org.pay_my_buddy.entity.commun.api.command.CommandApi;
import org.pay_my_buddy.entity.commun.api.event.Event;
import org.pay_my_buddy.entity.commun.api.event.EventApi;
import org.pay_my_buddy.entity.commun.api.query.Query;
import org.pay_my_buddy.entity.commun.api.query.QueryApi;

/**
 * This interface defines the contract for an API provider.
 * An API provider is responsible for providing access to the different buses in the system.
 * These buses include the QueryApi, CommandApi, and EventApi.
 */
public interface ApiProvider {

    CommandApi getCommandApi();

    EventApi getEventApi();

    QueryApi getQueryApi();

    /**
     * This method is responsible for dispatching a command to its respective handler.
     *
     * @param command The command to execute.
     * @param <C>     The type of the command.
     */
    default <C extends Command> void execute(C command) {
        getCommandApi().execute(command);
    }

    /**
     * This method is responsible for dispatching an event to its respective handler.
     *
     * @param event The event to publish.
     * @param <E>   The type of the event.
     */
    default <E extends Event> void publish(E event) {
        getEventApi().publish(event);
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