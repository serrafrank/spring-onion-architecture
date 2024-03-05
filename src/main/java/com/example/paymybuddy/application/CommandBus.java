package com.example.paymybuddy.application;

import com.example.paymybuddy.application.shared.use_case.command.Command;

public interface CommandBus {
    void dispatch(Command command);
}
