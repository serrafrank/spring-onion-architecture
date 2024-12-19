package org.pay_my_buddy.core.framework.domain.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.core.framework.domain.exception.BadArgumentException;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("EventId unit tests")
class EventIdTest {
    final static String validValue = "EVENT_01JFFP2KTSPK8FRHGSBCD21T8T";
    final static String invalidValue = "INVALID_123456";
    @Test
    @DisplayName("GIVEN a valid value WHEN EventId is created THEN it should succeed")
    void testEventIdWithValidValue() {
        // WHEN
        final EventId eventId = new EventId(validValue);

        // THEN
        assertThat(eventId).isNotNull();
        assertThat(eventId.value()).isEqualTo(validValue);
    }

    @Test
    @DisplayName("GIVEN a null value WHEN EventId is created THEN it should throw an exception")
    void testEventIdWithNullValue() {
        // GIVEN
        final String nullValue = null;

        // WHEN/THEN
        assertThatThrownBy(() -> new EventId(nullValue))
                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class)
                .hasMessageContaining("EventId is required");
    }

    @Test
    @DisplayName("GIVEN an invalid value WHEN EventId is created THEN it should throw an exception")
    void testEventIdWithInvalidValue() {
        // GIVEN
        final String invalidValue = "INVALID_123";

        // WHEN/THEN
        assertThatThrownBy(() -> new EventId(invalidValue))
                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class)
                .hasMessageContaining("EventId not valid with value = " + invalidValue);
    }

    @Test
    @DisplayName("GIVEN no value WHEN EventId is created THEN it should generate a valid ID")
    void testDefaultEventIdConstructor() {
        // WHEN
        final EventId eventId = new EventId();

        // THEN
        assertThat(eventId).isNotNull();
        assertThat(eventId.value()).startsWith("EVENT_");
        assertThat(EntityId.isValid(eventId.value(), "EVENT_")).isTrue();
    }

    @Test
    @DisplayName("GIVEN a valid EntityId WHEN EventId is created from it THEN it should succeed")
    void testEventIdFromEntityId() {
        // GIVEN
        final EntityId entityId = () -> validValue;

        // WHEN
        final EventId eventId = new EventId(entityId);

        // THEN
        assertThat(eventId).isNotNull();
        assertThat(eventId.value()).isEqualTo(entityId.value());
    }

    @Test
    @DisplayName("GIVEN an invalid EntityId WHEN EventId is created from it THEN it should throw an exception")
    void testEventIdFromInvalidEntityId() {
        // GIVEN
        final EntityId invalidEntityId = () -> invalidValue;

        // WHEN/THEN
        assertThatThrownBy(() -> new EventId(invalidEntityId))
                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class)
                .hasMessageContaining("EventId not valid with value = " + invalidEntityId.value());
    }
}
