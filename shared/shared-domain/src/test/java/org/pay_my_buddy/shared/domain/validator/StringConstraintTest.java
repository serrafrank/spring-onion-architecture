package org.pay_my_buddy.shared.domain.validator;

import org.junit.jupiter.api.Test;
import org.pay_my_buddy.shared.domain.exception.BadArgumentException;

import static org.junit.jupiter.api.Assertions.*;

class StringConstraintTest {

    @Test
    void notBlank_withBlankValue() {
        StringConstraint constraint = Constraint.checkIf("");
        assertThrows(BadArgumentException.class, constraint::notBlank);
    }

    @Test
    void notBlank_withBlankString() {
        StringConstraint constraint = new StringConstraint(" ");
        assertThrows(BadArgumentException.class, constraint::notBlank);
    }

    @Test
    void notBlank_withCustomMessage() {
        StringConstraint constraint = new StringConstraint(" ");
        BadArgumentException exception = assertThrows(BadArgumentException.class, () -> constraint.notBlank("Custom message"));
        assertEquals("Custom message", exception.getMessage());
    }

    @Test
    void notEmpty_withNonEmptyString() {
        StringConstraint constraint = new StringConstraint("value");
        assertDoesNotThrow(() -> constraint.notEmpty());
    }

    @Test
    void notEmpty_withEmptyString() {
        StringConstraint constraint = new StringConstraint("");
        assertThrows(BadArgumentException.class, constraint::notEmpty);
    }

    @Test
    void length_withCorrectLength() {
        StringConstraint constraint = new StringConstraint("value");
        assertDoesNotThrow(() -> constraint.length(5));
    }

    @Test
    void length_withIncorrectLength() {
        StringConstraint constraint = new StringConstraint("value");
        assertThrows(BadArgumentException.class, () -> constraint.length(4));
    }

    @Test
    void minLength_withSufficientLength() {
        StringConstraint constraint = new StringConstraint("value");
        assertDoesNotThrow(() -> constraint.minLength(3));
    }

    @Test
    void minLength_withInsufficientLength() {
        StringConstraint constraint = new StringConstraint("val");
        assertThrows(BadArgumentException.class, () -> constraint.minLength(4));
    }

    @Test
    void maxLength_withSufficientLength() {
        StringConstraint constraint = new StringConstraint("value");
        assertDoesNotThrow(() -> constraint.maxLength(5));
    }

    @Test
    void maxLength_withExcessiveLength() {
        StringConstraint constraint = new StringConstraint("value");
        assertThrows(BadArgumentException.class, () -> constraint.maxLength(4));
    }

    @Test
    void matches_withMatchingPattern() {
        StringConstraint constraint = new StringConstraint("value");
        assertDoesNotThrow(() -> constraint.matches("value"));
    }

    @Test
    void matches_withNonMatchingPattern() {
        StringConstraint constraint = new StringConstraint("value");
        assertThrows(BadArgumentException.class, () -> constraint.matches("different"));
    }

    @Test
    void contains_withContainedSequence() {
        StringConstraint constraint = new StringConstraint("value");
        assertDoesNotThrow(() -> constraint.contains("val"));
    }

    @Test
    void contains_withNonContainedSequence() {
        StringConstraint constraint = new StringConstraint("value");
        assertThrows(BadArgumentException.class, () -> constraint.contains("different"));
    }

    @Test
    void startsWith_withMatchingPrefix() {
        StringConstraint constraint = new StringConstraint("value");
        assertDoesNotThrow(() -> constraint.startsWith("val"));
    }

    @Test
    void startsWith_withNonMatchingPrefix() {
        StringConstraint constraint = new StringConstraint("value");
        assertThrows(BadArgumentException.class, () -> constraint.startsWith("different"));
    }

    @Test
    void endsWith_withMatchingSuffix() {
        StringConstraint constraint = new StringConstraint("value");
        assertDoesNotThrow(() -> constraint.endsWith("lue"));
    }

    @Test
    void endsWith_withNonMatchingSuffix() {
        StringConstraint constraint = new StringConstraint("value");
        assertThrows(BadArgumentException.class, () -> constraint.endsWith("different"));
    }

    @Test
    void isLowerCase_withLowerCaseString() {
        StringConstraint constraint = new StringConstraint("value");
        assertDoesNotThrow(() -> constraint.isLowerCase());
    }

    @Test
    void isLowerCase_withNonLowerCaseString() {
        StringConstraint constraint = new StringConstraint("Value");
        assertThrows(BadArgumentException.class, constraint::isLowerCase);
    }

    @Test
    void isUpperCase_withUpperCaseString() {
        StringConstraint constraint = new StringConstraint("VALUE");
        assertDoesNotThrow(() -> constraint.isUpperCase());
    }

    @Test
    void isUpperCase_withNonUpperCaseString() {
        StringConstraint constraint = new StringConstraint("Value");
        assertThrows(BadArgumentException.class, constraint::isUpperCase);
    }

    @Test
    void email_withValidEmail() {
        StringConstraint constraint = new StringConstraint("test@example.com");
        assertDoesNotThrow(() -> constraint.email());
    }

    @Test
    void email_withInvalidEmail() {
        StringConstraint constraint = new StringConstraint("invalid-email");
        assertThrows(BadArgumentException.class, constraint::email);
    }
}
