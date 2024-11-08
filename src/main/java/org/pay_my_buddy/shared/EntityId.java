package org.pay_my_buddy.shared;

import java.util.UUID;

public class EntityId implements ValueObject<UUID> {


	// The value of the ID.
	private final UUID value;

	protected EntityId() {
		this(UUID.randomUUID());
	}

	protected EntityId(UUID id) {
		if (id == null) {
			throw new IllegalArgumentException("ID cannot be null");
		}
		this.value = id;
	}

	protected EntityId(EntityId id) {
		if (id == null) {
			throw new IllegalArgumentException("ID cannot be null");
		}
		this.value = id.value();
	}

	protected EntityId(String id) {
		if (id == null) {
			throw new IllegalArgumentException("ID cannot be null");
		}
		if (id.isBlank()) {
			throw new IllegalArgumentException("ID cannot be blank");
		}
		this.value = UUID.fromString(id);
	}

	public static EntityId createRandomUnique() {
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
