package com.example.paymybuddy.application;

public interface CommandBus {
    void dispatch(Command command);
}
