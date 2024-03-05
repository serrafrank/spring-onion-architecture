package com.example.paymybuddy.application;

import com.example.paymybuddy.application.shared.use_case.query.Query;

public interface QueryBus {
    <R> R ask(Query<R> query);
}
