package com.example.paymybuddy.application.shared.message_handler;

public interface QueryHandler<T extends Query, R> extends Handler<Query> {

    R execute(T command);
}