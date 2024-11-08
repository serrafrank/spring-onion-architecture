package org.pay_my_buddy.shared.exchange.user;

import java.util.Set;

public interface UserEntityProjection {
	UserId id();

	String firstname();

	String lastname();

	String email();

	String password();

	Set<UserId> friends();
}
