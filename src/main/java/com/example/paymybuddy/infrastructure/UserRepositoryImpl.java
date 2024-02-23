package com.example.paymybuddy.infrastructure;

import com.example.paymybuddy.application.user.UserRepository;
import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.user.UserAggregate;
import com.example.paymybuddy.core.user.valueobject.UserId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public Optional<UserAggregate> findUser(Id id) {
        return Optional.empty();
    }

    @Override
    public UserId saveUser(UserAggregate user) {
        return null;
    }

    @Override
    public boolean existsById(Id id) {
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }
}
