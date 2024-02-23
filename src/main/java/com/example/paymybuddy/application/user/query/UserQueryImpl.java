package com.example.paymybuddy.application.user.query;

import com.example.paymybuddy.application.user.UserService;
import com.example.paymybuddy.core.user.UserAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserQueryImpl implements UserQuery {
    private final UserService userService;

    @Override
    public Optional<UserAggregate> findUser(FindUserByIdQuery findUserByIdQuery) {
        return userService.findUser(findUserByIdQuery.id());
    }

    @Override
    public boolean existsUser(ExistsUserByIdQuery existsUserByIdQuery) {
        return userService.existsUser(existsUserByIdQuery.id());
    }

    @Override
    public boolean existsUser(ExistsUserByEmailQuery existsUserByEmailQuery) {
        return userService.existsUser(existsUserByEmailQuery.email());
    }

    @Override
    public boolean areFriends(AreFriendsQuery areFriendsQuery) {
        return userService.areFriends(areFriendsQuery.userId(), areFriendsQuery.friendId());
    }
}
