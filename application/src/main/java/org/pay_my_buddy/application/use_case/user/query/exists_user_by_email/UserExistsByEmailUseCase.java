package org.pay_my_buddy.application.use_case.user.query.exists_user_by_email;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.application.common.DomainService;
import org.pay_my_buddy.application.common.api.QueryHandler;
import org.pay_my_buddy.application.use_case.user.UserSpi;

@DomainService
@RequiredArgsConstructor
public class UserExistsByEmailUseCase implements QueryHandler<UserExistsByEmailQuery, Boolean> {

    private final UserSpi userSpi;

    @Override
    public Boolean handle(UserExistsByEmailQuery query) {
        return userSpi.existsByEmail(query.email());
    }
}
