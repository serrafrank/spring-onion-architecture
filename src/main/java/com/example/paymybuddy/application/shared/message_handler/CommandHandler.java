package com.example.paymybuddy.application.shared.message_handler;

public interface CommandHandler<T extends Command> extends Handler<Command> {

    void execute(T command);

}