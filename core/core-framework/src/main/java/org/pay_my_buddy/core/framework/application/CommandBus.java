package org.pay_my_buddy.core.framework.application;

import org.pay_my_buddy.core.framework.domain.message.Command;

public interface CommandBus {

    <COMMAND extends Command> void execute(COMMAND command);

}
