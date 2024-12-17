package org.pay_my_buddy.core.command.application;


import org.pay_my_buddy.core.framework.domain.UseCaseHandler;
import org.pay_my_buddy.core.framework.domain.message.Command;

@FunctionalInterface
public interface CommandHandler<COMMAND extends Command, RESPONSE> extends UseCaseHandler<COMMAND, RESPONSE> {

    RESPONSE handle(COMMAND command);

}
