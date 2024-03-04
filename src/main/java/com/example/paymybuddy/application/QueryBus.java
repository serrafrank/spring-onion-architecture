package com.example.paymybuddy.application;

public interface QueryBus {
    <R> R ask(Query<R> query);
}
