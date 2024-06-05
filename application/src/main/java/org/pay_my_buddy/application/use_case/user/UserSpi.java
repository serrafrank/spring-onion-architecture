package org.pay_my_buddy.application.use_case.user;

import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.user.Email;
import org.pay_my_buddy.entity.user.User;

import java.util.List;
import java.util.Optional;


public interface UserSpi {

    List<User> findAll();

    Optional<User> findUser(Id id);

    boolean existsById(Id id);

    boolean existsByEmail(Email email);

    void save(User user);
}
