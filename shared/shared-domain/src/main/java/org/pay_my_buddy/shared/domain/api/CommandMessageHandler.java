package org.pay_my_buddy.shared.domain.api;

@FunctionalInterface
public interface CommandMessageHandler<COMMAND extends Command> extends MessageHandler<COMMAND> {

}