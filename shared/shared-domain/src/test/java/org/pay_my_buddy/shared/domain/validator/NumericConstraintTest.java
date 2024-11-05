package org.pay_my_buddy.shared.domain.validator;

import org.junit.jupiter.api.Test;
import org.pay_my_buddy.shared.domain.exception.BadArgumentException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NumericConstraintTest {

    @Test
    void isPositive_withPositiveNumber() {
        NumericConstraint constraint = Constraint.checkIf(5);
        assertDoesNotThrow(() -> constraint.isPositive());
    }

    @Test
    void isPositive_withZero() {
        NumericConstraint constraint = Constraint.checkIf(0);
        assertThrows(BadArgumentException.class, constraint::isPositive);
    }

    @Test
    void isPositive_withNegativeNumber() {
        NumericConstraint constraint = Constraint.checkIf(-5);
        assertThrows(BadArgumentException.class, constraint::isPositive);
    }

    @Test
    void isNegative_withNegativeNumber() {
        NumericConstraint constraint = Constraint.checkIf(-5);
        assertDoesNotThrow(() -> constraint.isNegative());
    }

    @Test
    void isNegative_withZero() {
        NumericConstraint constraint = Constraint.checkIf(0);
        assertThrows(BadArgumentException.class, constraint::isNegative);
    }

    @Test
    void isNegative_withPositiveNumber() {
        NumericConstraint constraint = Constraint.checkIf(5);
        assertThrows(BadArgumentException.class, constraint::isNegative);
    }

    @Test
    void isZero_withZero() {
        NumericConstraint constraint = Constraint.checkIf(0);
        assertDoesNotThrow(() -> constraint.isZero());
    }

    @Test
    void isZero_withPositiveNumber() {
        NumericConstraint constraint = Constraint.checkIf(5);
        assertThrows(BadArgumentException.class, constraint::isZero);
    }

    @Test
    void isZero_withNegativeNumber() {
        NumericConstraint constraint = Constraint.checkIf(-5);
        assertThrows(BadArgumentException.class, constraint::isZero);
    }

    @Test
    void isGreaterThan_withGreaterValue() {
        NumericConstraint constraint = Constraint.checkIf(10);
        assertDoesNotThrow(() -> constraint.isGreaterThan(5));
    }

    @Test
    void isGreaterThan_withEqualValue() {
        NumericConstraint constraint = Constraint.checkIf(5);
        assertThrows(BadArgumentException.class, () -> constraint.isGreaterThan(5));
    }

    @Test
    void isGreaterThan_withLesserValue() {
        NumericConstraint constraint = Constraint.checkIf(5);
        assertThrows(BadArgumentException.class, () -> constraint.isGreaterThan(10));
    }

    @Test
    void isLessThan_withLesserValue() {
        NumericConstraint constraint = Constraint.checkIf(5);
        assertDoesNotThrow(() -> constraint.isLessThan(10));
    }

    @Test
    void isLessThan_withEqualValue() {
        NumericConstraint constraint = Constraint.checkIf(5);
        assertThrows(BadArgumentException.class, () -> constraint.isLessThan(5));
    }

    @Test
    void isLessThan_withGreaterValue() {
        NumericConstraint constraint = Constraint.checkIf(10);
        assertThrows(BadArgumentException.class, () -> constraint.isLessThan(5));
    }

    @Test
    void isBetween_withValueInRange() {
        NumericConstraint constraint = Constraint.checkIf(5);
        assertDoesNotThrow(() -> constraint.isBetween(1, 10));
    }

    @Test
    void isBetween_withValueEqualToMin() {
        NumericConstraint constraint = Constraint.checkIf(1);
        assertDoesNotThrow(() -> constraint.isBetween(1, 10));
    }

    @Test
    void isBetween_withValueEqualToMax() {
        NumericConstraint constraint = Constraint.checkIf(10);
        assertDoesNotThrow(() -> constraint.isBetween(1, 10));
    }

    @Test
    void isBetween_withValueOutOfRange() {
        NumericConstraint constraint = Constraint.checkIf(11);
        assertThrows(BadArgumentException.class, () -> constraint.isBetween(1, 10));
    }

    @Test
    void isPositiveOrZero_withPositiveNumber() {
        NumericConstraint constraint = Constraint.checkIf(5);
        assertDoesNotThrow(() -> constraint.isPositiveOrZero());
    }

    @Test
    void isPositiveOrZero_withZero() {
        NumericConstraint constraint = Constraint.checkIf(0);
        assertDoesNotThrow(() -> constraint.isPositiveOrZero());
    }

    @Test
    void isPositiveOrZero_withNegativeNumber() {
        NumericConstraint constraint = Constraint.checkIf(-5);
        assertThrows(BadArgumentException.class, constraint::isPositiveOrZero);
    }

    @Test
    void isNegativeOrZero_withNegativeNumber() {
        NumericConstraint constraint = Constraint.checkIf(-5);
        assertDoesNotThrow(() -> constraint.isNegativeOrZero());
    }

    @Test
    void isNegativeOrZero_withZero() {
        NumericConstraint constraint = Constraint.checkIf(0);
        assertDoesNotThrow(() -> constraint.isNegativeOrZero());
    }

    @Test
    void isNegativeOrZero_withPositiveNumber() {
        NumericConstraint constraint = Constraint.checkIf(5);
        assertThrows(BadArgumentException.class, constraint::isNegativeOrZero);
    }

    @Test
    void isNotEqualTo_withDifferentValue() {
        NumericConstraint constraint = Constraint.checkIf(5);
        assertDoesNotThrow(() -> constraint.isNotEqualTo(10));
    }

    @Test
    void isNotEqualTo_withSameValue() {
        NumericConstraint constraint = Constraint.checkIf(5);
        assertThrows(BadArgumentException.class, () -> constraint.isNotEqualTo(5));
    }
}