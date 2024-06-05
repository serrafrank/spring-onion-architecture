package org.pay_my_buddy.infrastructure.user;

import org.pay_my_buddy.application.use_case.user.UserSpi;
import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.user.Email;
import org.pay_my_buddy.entity.user.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryUserRepository implements UserSpi {

    private final List<User> users = new ArrayList<>();

    @Override
    public Optional<User> findUser(Id id) {
        return
                users.stream()
                        .filter(user -> user.id().equals(id))
                        .reduce((a, b) -> {
                            throw new IllegalStateException("Multiple users found for id: " + id);
                        });
    }

    @Override
    public boolean existsById(Id id) {
        return
                users.stream()
                        .anyMatch(user -> user.id().equals(id));
    }

    @Override
    public boolean existsByEmail(Email email) {
        return
                users.stream()
                        .anyMatch(user -> user.email().equals(email));
    }

    @Override
    public void save(User user) {
        users.stream()
                .filter(u -> u.id().equals(user.id()))
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple users found for id: " + user.id());
                })
                .ifPresentOrElse(
                        u -> {
                            users.remove(u);
                            users.add(user);
                        },
                        () -> users.add(user)
                );
    }
}
