package org.pay_my_buddy.core.framework.domain.value_object;

public interface EntityId extends ValueObject<String> {

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

    String value();
}
