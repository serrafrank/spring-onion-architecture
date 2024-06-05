package org.pay_my_buddy.application.common.api;

/**
 * The CommandHandler interface is the contract for all command handlers in the application.
 * It provides a method to execute a command.
 * Each specific command handler will implement this interface.
 *
 * @param <C> The type of the command, which extends AbstractCommand.
 */
public interface CommandHandler<C extends Command, R> extends Handler<C> {

    CommandResponse<R> handle(C command);
}