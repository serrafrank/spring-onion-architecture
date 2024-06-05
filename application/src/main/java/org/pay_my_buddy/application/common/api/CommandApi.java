package org.pay_my_buddy.application.common.api;

/**
 * The CommandApi interface is the entry point for all commands in the application.
 * It is responsible for dispatching commands to their respective handlers.
 */
public interface CommandApi {

    <C extends Command<R>, R> R execute(C command);
}