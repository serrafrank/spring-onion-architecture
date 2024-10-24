package org.pay_my_buddy.user.domain;

import org.pay_my_buddy.shared.domain.api.DomainSpi;

public interface UserSpi extends DomainSpi<User, UserId> {

    boolean emailAlreadyExists(String email);

    void delete(User user);

}
