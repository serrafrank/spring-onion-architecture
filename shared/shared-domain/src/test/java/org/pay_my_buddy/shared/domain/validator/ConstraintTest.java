package org.pay_my_buddy.shared.domain.validator;

import org.junit.jupiter.api.Test;

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
