package org.pay_my_buddy.shared.domain.api.command;

public interface CommandBus {

    <COMMAND extends Command> void execute(COMMAND command);

}