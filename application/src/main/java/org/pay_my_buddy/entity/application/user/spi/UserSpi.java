package org.pay_my_buddy.entity.application.user.spi;

import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.user.Email;
import org.pay_my_buddy.entity.user.User;

import java.util.Optional;


public interface UserSpi {


    Optional<User> findUser(Id id);

    boolean existsById(Id id);

    boolean existsByEmail(Email email);

    void save(User user);
}
