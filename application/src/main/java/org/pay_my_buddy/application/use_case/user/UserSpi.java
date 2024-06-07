package org.pay_my_buddy.application.use_case.user;

import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.user.Email;
import org.pay_my_buddy.entity.user.User;

import java.util.List;
import java.util.Optional;


public interface UserSpi {

    default List<User> findAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    default Optional<User> findUser(Id id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    default Optional<User> findUser(Email email) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    default boolean existsById(Id id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    default boolean existsByEmail(Email email) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    default void save(User user) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
