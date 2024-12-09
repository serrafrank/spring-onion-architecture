package org.pay_my_buddy.api_command;

@FunctionalInterface
public interface CommandHandler<COMMAND extends Command> {
    void handle(COMMAND command);

}
