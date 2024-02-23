package com.example.paymybuddy.application.user.query;

import com.example.paymybuddy.core.user.UserAggregate;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

public interface UserQuery {

    Optional<UserAggregate> findUser(@Validated FindUserByIdQuery findUserByIdQuery);

    boolean existsUser(@Validated ExistsUserByIdQuery existsUserByIdQuery);

    boolean existsUser(@Validated ExistsUserByEmailQuery existsUserByEmailQuery);

    boolean areFriends(@Validated AreFriendsQuery areFriendsQuery);

}
