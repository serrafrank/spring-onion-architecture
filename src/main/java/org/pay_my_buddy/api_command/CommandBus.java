package org.pay_my_buddy.api_command;

public interface CommandBus {

	<COMMAND extends Command> void execute(COMMAND command);

}
