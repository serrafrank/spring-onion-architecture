package org.pay_my_buddy.user.query.domain;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.common.DomainService;
import org.pay_my_buddy.shared.query.domain.QueryHandler;
import org.pay_my_buddy.user.common.domain.FindUserByIdQuery;
import org.pay_my_buddy.user.common.domain.UserEntityProjection;

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
