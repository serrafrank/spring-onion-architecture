package org.pay_my_buddy.modules.user.query.application;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.framework.domain.DomainService;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.core.framework.domain.exception.NotFoundException;
import org.pay_my_buddy.core.query.application.QueryHandler;
import org.pay_my_buddy.modules.user.shared.UserState;
import org.pay_my_buddy.modules.user.shared.query.UserIsActiveQuery;

@DomainService
@RequiredArgsConstructor
public class UserIsActiveUseCase implements QueryHandler<UserIsActiveQuery, Boolean> {

    private final UserSpi userSpi;

    @Override
    public Boolean handle(UserIsActiveQuery query) {
        return userSpi.findUserById(query.id())
                .map(u -> u.currentState() == UserState.ACTIVE)
                .orElseThrow(() -> BusinessException.wrap(new NotFoundException("User not found with id: " + query.id())));
    }
}
