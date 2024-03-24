package org.pay_my_buddy.entity.user.spi;

import org.pay_my_buddy.entity.commun.entity.Id;
import org.pay_my_buddy.entity.user.User;
import org.pay_my_buddy.entity.user.UserId;

import java.util.Optional;


public interface UserSpi {


    Optional<User> findUser(Id id);

    boolean existsById(Id id);

    boolean existsByEmail(String email);

    void save(User user);
}
