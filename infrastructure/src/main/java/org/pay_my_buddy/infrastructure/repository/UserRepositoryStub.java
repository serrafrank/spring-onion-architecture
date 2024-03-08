package org.pay_my_buddy.infrastructure.repository;

import org.pay_my_buddy.entity.commun.entity.Id;
import org.pay_my_buddy.entity.user.User;
import org.pay_my_buddy.entity.user.UserId;
import org.pay_my_buddy.entity.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryStub implements UserRepository {
    @Override
    public Optional<User> findUser(Id id) {
        return Optional.empty();
    }

    @Override
    public UserId saveUser(User user) {
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

    @Override
    public User save(User user) {
        return null;
    }
}
