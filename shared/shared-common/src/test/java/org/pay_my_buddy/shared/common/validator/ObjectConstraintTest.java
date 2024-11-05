package org.pay_my_buddy.shared.common.validator;

import org.junit.jupiter.api.Test;
import org.pay_my_buddy.shared.common.domain.exception.BadArgumentException;
import org.pay_my_buddy.shared.common.domain.validator.Constraint;
import org.pay_my_buddy.shared.common.domain.validator.ObjectConstraint;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ObjectConstraintTest {

    @Test
    void isNotNull_withNullValue() {
        ObjectConstraint<String> constraint = Constraint.checkIf((String) null);
        assertThrows(BadArgumentException.class, constraint::isNotNull);
    }

    @Test
    void notNull_withIsNotNullValue() {
        ObjectConstraint<String> constraint = Constraint.checkIf("value");
        assertDoesNotThrow(() -> constraint.isNotNull());
    }

    @Test
    void isNotNull_withNullValueAndCustomMessage() {
        ObjectConstraint<String> constraint = Constraint.checkIf((String) null);
        assertThrows(BadArgumentException.class, () -> constraint.isNotNull("Custom message"));
    }

    @Test
    void isNotNull_withNullValueAndCustomThrowableSupplier() {
        ObjectConstraint<String> constraint = Constraint.checkIf((String) null);
        assertThrows(BadArgumentException.class, () -> constraint.isNotNull(() -> new BadArgumentException("Custom message")));
    }

    @Test
    void notNull_withIsNotNullValueAndCustomMessage() {
        ObjectConstraint<String> constraint = Constraint.checkIf("value");
        assertDoesNotThrow(() -> constraint.isNotNull("Custom message"));
    }

    @Test
    void notNull_withIsNotNullValueAndCustomThrowableSupplier() {
        ObjectConstraint<String> constraint = Constraint.checkIf("value");
        assertDoesNotThrow(() -> constraint.isNotNull(() -> new BadArgumentException("Custom message")));
    }

}
