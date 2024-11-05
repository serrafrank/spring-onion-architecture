package org.pay_my_buddy.shared.common.domain.api.command;

public interface CommandBus {

    <COMMAND extends Command> void execute(COMMAND command);

}
