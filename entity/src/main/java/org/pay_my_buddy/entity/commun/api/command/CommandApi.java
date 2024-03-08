package org.pay_my_buddy.entity.commun.api.command;

/**
 * The CommandApi interface is the entry point for all commands in the application.
 * It is responsible for dispatching commands to their respective handlers.
 */
public interface CommandApi {

    <C extends Command> void execute(C command);
}