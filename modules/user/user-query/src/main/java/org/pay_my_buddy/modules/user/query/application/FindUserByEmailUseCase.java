package org.pay_my_buddy.modules.user.query.application;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.framework.domain.DomainService;
import org.pay_my_buddy.core.query.application.QueryHandler;
import org.pay_my_buddy.modules.user.shared.UserEntityProjection;
import org.pay_my_buddy.modules.user.shared.query.FindUserByEmailQuery;

import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class FindUserByEmailUseCase implements QueryHandler<FindUserByEmailQuery, Optional<UserEntityProjection>> {

    private final UserSpi userSpi;

    @Override
    public Optional<UserEntityProjection> handle(FindUserByEmailQuery query) {
        return userSpi.findUserByEmail(query.email());
    }
}
