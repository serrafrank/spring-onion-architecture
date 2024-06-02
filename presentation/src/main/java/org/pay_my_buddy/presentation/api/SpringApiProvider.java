package org.pay_my_buddy.presentation.api;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.common.api.ApiProvider;
import org.pay_my_buddy.entity.common.api.command.CommandApi;
import org.pay_my_buddy.entity.common.api.event.EventApi;
import org.pay_my_buddy.entity.common.api.query.QueryApi;
import org.springframework.stereotype.Component;

/**
 * This class is a Spring specific implementation of the ApiProvider interface.
 * It is annotated with @Component, meaning that Spring will automatically create an instance of this class and manage it.
 * It uses Lombok's @RequiredArgsConstructor to automatically generate a constructor that initializes final fields.
 * The final fields are automatically injected by Spring due to the @RequiredArgsConstructor annotation.
 * It provides access to the CommandApi, EventApi, and QueryApi instances.
 */
@Component
@RequiredArgsConstructor
public class SpringApiProvider implements ApiProvider {

    /**
     * The CommandApi instance. This is automatically injected by Spring.
     */
    private final CommandApi commandApi;

    /**
     * The QueryApi instance. This is automatically injected by Spring.
     */
    private final QueryApi queryApi;

    /**
     * Returns the CommandApi instance.
     * This method is an implementation of the getCommandApi method defined in the ApiProvider interface.
     *
     * @return the CommandApi instance
     */
    @Override
    public CommandApi getCommandApi() {
        return commandApi;
    }

    /**
     * Returns the QueryApi instance.
     * This method is an implementation of the getQueryApi method defined in the ApiProvider interface.
     *
     * @return the QueryApi instance
     */
    @Override
    public QueryApi getQueryApi() {
        return queryApi;
    }
}