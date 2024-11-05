package org.pay_my_buddy.user.query.domain;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.common.DomainService;
import org.pay_my_buddy.shared.query.domain.QueryHandler;
import org.pay_my_buddy.user.common.domain.FindUserByEmailQuery;
import org.pay_my_buddy.user.common.domain.UserEntityProjection;

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
