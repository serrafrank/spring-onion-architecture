package com.example.paymybuddy.application.user;

import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.user.UserAggregate;
import com.example.paymybuddy.core.user.valueobject.UserId;
import org.springframework.stereotype.Component;

import java.util.Optional;


public interface UserRepository {

    Optional<UserAggregate> findUser(Id id);

    UserId saveUser(UserAggregate user);

    boolean existsById(Id id);

    boolean existsByEmail(String email);
}
