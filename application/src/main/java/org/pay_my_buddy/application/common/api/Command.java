package org.pay_my_buddy.application.common.api;

/**
 * This is the Command interface.
 * It serves as a marker interface for all command classes in the application.
 * A command represents an operation or action that the application can perform.
 * Each specific command will implement this interface.
 */
public sealed interface Command extends Request permits AbstractCommand {

}