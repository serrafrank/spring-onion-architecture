package org.pay_my_buddy.shared.common.validator;

import org.junit.jupiter.api.Test;
import org.pay_my_buddy.shared.common.domain.validator.Constraint;
import org.pay_my_buddy.shared.common.domain.validator.NumericConstraint;
import org.pay_my_buddy.shared.common.domain.validator.ObjectConstraint;
import org.pay_my_buddy.shared.common.domain.validator.StringConstraint;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ConstraintTest {

    @Test
    void checkIfReturnsObjectConstraintForObject() {
        Object value = new Object();
        assertInstanceOf(ObjectConstraint.class, Constraint.checkIf(value));
    }

    @Test
    void checkIfReturnsNumericConstraintForNumber() {
        Number value = 42;
        assertInstanceOf(NumericConstraint.class, Constraint.checkIf(value));
    }

    @Test
    void checkIfReturnsStringConstraintForString() {
        String value = "test";
        assertInstanceOf(StringConstraint.class, Constraint.checkIf(value));
    }

    @Test
    void checkIfReturnsObjectConstraintForNullObject() {
        assertInstanceOf(ObjectConstraint.class, Constraint.checkIf((Object) null));
    }

    @Test
    void checkIfReturnsNumericConstraintForNullNumber() {
        assertInstanceOf(NumericConstraint.class, Constraint.checkIf((Number) null));
    }

    @Test
    void checkIfReturnsStringConstraintForNullString() {
        assertInstanceOf(StringConstraint.class, Constraint.checkIf((String) null));
    }
}
