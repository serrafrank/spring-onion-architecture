package org.pay_my_buddy.user.domain;

import java.util.Optional;

public interface UserProjectionApi {

    Optional<User> findById(UserId id);

}
