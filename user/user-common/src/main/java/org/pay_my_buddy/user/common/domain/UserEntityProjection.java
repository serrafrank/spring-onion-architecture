package org.pay_my_buddy.user.common.domain;

import org.pay_my_buddy.shared.common.domain.entity.EntityId;

import java.util.Set;

public interface UserEntityProjection {
	EntityId id();

	String firstname();

	String lastname();

	String email();

	String password();

	Set<EntityId> friends();
}
