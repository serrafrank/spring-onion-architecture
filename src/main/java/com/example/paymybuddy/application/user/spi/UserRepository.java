package com.example.paymybuddy.application.user.spi;

import com.example.paymybuddy.application.user.domain.UserAggregate;
import com.example.paymybuddy.application.user.domain.UserId;
import com.example.paymybuddy.application.shared.entity.id.Id;

import java.util.Optional;


public interface UserRepository {


    Optional<UserAggregate> findUser(Id id);

    UserId saveUser(UserAggregate user);

    boolean existsById(Id id);

    boolean existsByEmail(String email);
    UserAggregate save(UserAggregate user);
}
