package com.example.paymybuddy.application.shared.message_handler;

public interface EventHandler<T extends Event> extends Handler<Event> {

    void dispatch(T command);

}