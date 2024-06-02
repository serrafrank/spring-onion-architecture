package org.pay_my_buddy.entity.common.api.command;

import org.pay_my_buddy.entity.common.api.Handler;

/**
 * The CommandHandler interface is the contract for all command handlers in the application.
 * It provides a method to execute a command.
 * Each specific command handler will implement this interface.
 *
 * @param <C> The type of the command, which extends Command.
 */
public interface CommandHandler<C extends Command> extends Handler<C> {

    EventList handle(C command);
}