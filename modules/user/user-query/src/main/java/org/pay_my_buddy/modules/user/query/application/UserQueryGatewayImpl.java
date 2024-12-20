package org.pay_my_buddy.modules.user.query.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.core.framework.domain.DomainService;
import org.pay_my_buddy.modules.user.shared.UserEntityProjection;
import org.pay_my_buddy.modules.user.shared.query.FindUserByEmailQuery;
import org.pay_my_buddy.modules.user.shared.query.FindUserByIdQuery;
import org.pay_my_buddy.modules.user.shared.query.UserIsActiveQuery;
import org.pay_my_buddy.modules.user.shared.query.UserQueryGateway;

import java.util.Optional;

@DomainService
@RequiredArgsConstructor
@Slf4j
public class UserQueryGatewayImpl implements UserQueryGateway {

    private final FindUserByEmailUseCase findUserByEmailUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final UserIsActiveUseCase userIsActiveUseCase;

    @Override
    public Optional<UserEntityProjection> handle(FindUserByEmailQuery query) {
        return findUserByEmailUseCase.handle(query);

    }

    @Override
    public Optional<UserEntityProjection> handle(FindUserByIdQuery query) {
        return findUserByIdUseCase.handle(query);
    }

    @Override
    public boolean handle(UserIsActiveQuery query) {
        return userIsActiveUseCase.handle(query);
    }
}
