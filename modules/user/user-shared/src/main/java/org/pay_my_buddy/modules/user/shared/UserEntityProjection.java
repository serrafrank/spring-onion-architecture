package org.pay_my_buddy.modules.user.shared;

import java.util.Set;

public interface UserEntityProjection {
    UserId userId();

    String firstname();

    String lastname();

    String email();

    String password();

    Set<UserId> friends();

    UserState currentState();
}
