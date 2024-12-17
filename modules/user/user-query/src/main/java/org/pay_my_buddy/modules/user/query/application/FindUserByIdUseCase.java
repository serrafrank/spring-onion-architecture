package org.pay_my_buddy.modules.user.query.application;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.framework.domain.DomainService;
import org.pay_my_buddy.core.query.application.QueryHandler;
import org.pay_my_buddy.modules.user.shared.UserEntityProjection;
import org.pay_my_buddy.modules.user.shared.query.FindUserByIdQuery;

import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class FindUserByIdUseCase implements QueryHandler<FindUserByIdQuery, Optional<UserEntityProjection>> {

    private final UserSpi userSpi;

    @Override
    public Optional<UserEntityProjection> handle(FindUserByIdQuery query) {
        return userSpi.findUserById(query.id());
    }
}
