package org.pay_my_buddy.core.framework.domain.value_object;

import org.pay_my_buddy.core.framework.domain.validator.Validate;

public interface EntityId extends ValueObject<String> {

    static EntityId of(String id) {
        return new EntityId() {
            @Override
            public String prefix() {
                return "";
            }

            @Override
            public String value() {
                return id;
            }
        };
    }

    static String generateId() {
        return new Ulid().create();
    }

    default void validate(String id) {
        Validate.checkIf(id).isNotNull("Id is required")
                .predicate(this::isValid, "Id format not valid : " + id);
    }

    default boolean isValid(String id) {
        if (id == null || prefix() == null || id.length() < prefix().length() || !id.startsWith(prefix())) {
            return false;
        }
        String value = id.substring(prefix().length());
        return Ulid.isValid(value);
    }

    String prefix();

    String value();
}
