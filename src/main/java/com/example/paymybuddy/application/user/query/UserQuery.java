package com.example.paymybuddy.application.user.query;

import com.example.paymybuddy.core.user.UserAggregate;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

public interface UserQuery {

    Optional<UserAggregate> findUser(FindUserByIdQuery findUserByIdQuery);

    boolean existsUser(ExistsUserByIdQuery existsUserByIdQuery);

    boolean existsUser(ExistsUserByEmailQuery existsUserByEmailQuery);

    boolean areFriends(AreFriendsQuery areFriendsQuery);

}
