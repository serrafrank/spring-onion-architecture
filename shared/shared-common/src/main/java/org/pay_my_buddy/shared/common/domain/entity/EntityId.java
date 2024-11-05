package org.pay_my_buddy.shared.common.domain.entity;

import java.io.Serializable;
import java.util.UUID;

/**
 * This is an interface that represents a unique identifier (ID) for an entity.
 * The ID is of an Object.
 * It declares two methods: value() and generateUniqueId().
 */
public class EntityId implements ValueObject<UUID>, Serializable {


	// The value of the ID.
	private final UUID value;

	protected EntityId() {
		this(UUID.randomUUID());
	}

	protected EntityId(UUID value) {
		this.value = value;
	}

	protected EntityId(EntityId id) {
		this(UUID.fromString(id.value().toString()));
	}

	protected EntityId(String id) {
		this(UUID.fromString(id));
	}

	public static EntityId newId() {
		return new EntityId();
	}

	public static EntityId of(EntityId id) {
		return new EntityId(id);
	}

	public static EntityId of(String id) {
		return new EntityId(id);
	}

	public static EntityId of(UUID uuid) {
		return new EntityId(uuid);
	}

	@Override
	public UUID value() {
		return value;
	}

	@Override
	public String toString() {
		return value().toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof EntityId that)) return false;
		return value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
}
