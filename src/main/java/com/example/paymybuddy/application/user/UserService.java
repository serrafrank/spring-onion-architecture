package com.example.paymybuddy.application.user;

import com.example.paymybuddy.application.user.domain.UserAggregate;
import com.example.paymybuddy.application.user.domain.UserId;
import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.common.value_object.Amount;

import java.util.Optional;

public interface UserService {
    Optional<UserAggregate> findUser(Id id);

    UserId createUser(String firstName, String lastName, String email, String password);

    void debitUser(UserId id, Amount amount);

    void creditUser(UserId id, Amount amount);


    boolean existsUser(UserId id);

    boolean existsUser(String email);

    boolean areFriends(UserId user1, UserId user2);
}
