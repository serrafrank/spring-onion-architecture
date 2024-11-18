package org.pay_my_buddy.shared;

public interface EntityId extends ValueObject<String> {

	String value();

	static String generateId() {
		return new Ulid().create();
	}

	static boolean isValid(String id, String prefix) {
		if (id == null || prefix == null || id.length() < prefix.length() || !id.startsWith(prefix)) {
			return false;
		}
		String value = id.substring(prefix.length());
		return Ulid.isValid(value);
	}

	static EntityId of(String id) {
		return () -> id;
	}
}
