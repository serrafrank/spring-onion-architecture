package org.pay_my_buddy.infrastructure.user;

import org.pay_my_buddy.entity.common.entity.Id;
import org.pay_my_buddy.entity.common.value_object.Email;
import org.pay_my_buddy.entity.user.User;
import org.pay_my_buddy.entity.user.spi.UserSpi;
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
                        .filter(user -> user.getId().equals(id))
                        .reduce((a, b) -> {
                            throw new IllegalStateException("Multiple users found for id: " + id);
                        });
    }

    @Override
    public boolean existsById(Id id) {
        return
                users.stream()
                        .anyMatch(user -> user.getId().equals(id));
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
                .filter(u -> u.getId().equals(user.getId()))
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple users found for id: " + user.getId());
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
