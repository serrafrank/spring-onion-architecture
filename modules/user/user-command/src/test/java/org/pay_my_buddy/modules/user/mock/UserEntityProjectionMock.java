package org.pay_my_buddy.modules.user.mock;

import lombok.Data;
import lombok.experimental.Accessors;
import org.pay_my_buddy.modules.user.shared.UserEntityProjection;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.UserState;

import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(fluent = true)
public class UserEntityProjectionMock implements UserEntityProjection {
    private UserId userId = new UserId();
    private String firstname = "John";
    private String lastname = "Doe";
    private String email = "john.doe@example.com";
    private String password = "password123";
    private Set<UserId> friends = new HashSet<>();
    private UserState currentState = UserState.ACTIVE;
}
