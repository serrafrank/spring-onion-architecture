package org.pay_my_buddy.shared.domain.api.command;

@FunctionalInterface
public interface CommandHandler<COMMAND extends Command> {
    void handle(COMMAND command);

}