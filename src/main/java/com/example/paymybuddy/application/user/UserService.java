package com.example.paymybuddy.application.user;

import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.common.valueobject.Amount;
import com.example.paymybuddy.core.user.UserAggregate;
import com.example.paymybuddy.core.user.valueobject.UserId;

import java.util.Optional;

public interface UserService {
    Optional<UserAggregate> findUser(Id id);

    UserId createUser(String firstName, String lastName, String email, String password);

    void debitUser(Id id, Amount amount);

    void creditUser(Id id, Amount amount);


    boolean existsUser(Id id);

    boolean existsUser(String email);

    boolean areFriends(Id user1, Id user2);
}
