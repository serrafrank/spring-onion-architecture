package org.pay_my_buddy.user.domain;

import org.pay_my_buddy.shared.domain.entity.EntityId;

import java.util.Set;

public interface UserEntityProjection {
	EntityId id();

	String firstname();

	String lastname();

	String email();

	String password();

	Set<EntityId> friends();
}
