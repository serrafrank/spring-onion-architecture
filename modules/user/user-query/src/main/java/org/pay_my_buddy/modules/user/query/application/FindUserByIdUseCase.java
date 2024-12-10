package org.pay_my_buddy.modules.user.query.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.query.application.QueryHandler;
import org.pay_my_buddy.modules.user.shared.UserEntityProjection;
import org.pay_my_buddy.modules.user.shared.query.FindUserByIdQuery;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserByIdUseCase implements QueryHandler<FindUserByIdQuery, Optional<UserEntityProjection>> {

    private final UserSpi userSpi;

    @Override
    public Optional<UserEntityProjection> handle(FindUserByIdQuery query) {
        return userSpi.findUserById(query.id());
    }
}
