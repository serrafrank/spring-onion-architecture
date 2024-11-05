package org.pay_my_buddy.user.domain.find_user_by_email;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.domain.DomainService;
import org.pay_my_buddy.shared.domain.api.query.QueryHandler;
import org.pay_my_buddy.user.domain.UserEntityProjection;
import org.pay_my_buddy.user.domain.UserSpi;

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
