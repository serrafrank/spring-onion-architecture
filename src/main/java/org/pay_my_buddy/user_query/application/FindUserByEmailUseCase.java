package org.pay_my_buddy.user_query.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.api_query.QueryHandler;
import org.pay_my_buddy.shared.exchange.user.UserEntityProjection;
import org.pay_my_buddy.shared.exchange.user.query.FindUserByEmailQuery;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserByEmailUseCase implements QueryHandler<FindUserByEmailQuery, Optional<UserEntityProjection>> {

    private final UserSpi userSpi;

    @Override
    public Optional<UserEntityProjection> handle(FindUserByEmailQuery query) {
        return userSpi.findUserByEmail(query.email());
    }
}
