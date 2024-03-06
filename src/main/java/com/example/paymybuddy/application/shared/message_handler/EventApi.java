package com.example.paymybuddy.application.shared.message_handler;

public interface EventApi {
    <T extends Event> void dispatch(T command);
}
