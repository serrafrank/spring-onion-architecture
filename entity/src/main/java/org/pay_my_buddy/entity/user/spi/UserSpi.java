package org.pay_my_buddy.entity.user.spi;

import org.pay_my_buddy.entity.common.entity.Id;
import org.pay_my_buddy.entity.common.value_object.Email;
import org.pay_my_buddy.entity.user.User;

import java.util.Optional;


public interface UserSpi {


    Optional<User> findUser(Id id);

    boolean existsById(Id id);

    boolean existsByEmail(Email email);

    void save(User user);
}
