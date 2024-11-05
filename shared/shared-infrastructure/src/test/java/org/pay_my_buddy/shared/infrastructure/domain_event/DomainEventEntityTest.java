package org.pay_my_buddy.shared.infrastructure.domain_event;

import org.junit.jupiter.api.Test;
import org.pay_my_buddy.shared.domain.api.Message;

import static org.junit.jupiter.api.Assertions.*;

class DomainEventEntityTest {

    @Test
    void constructor_withMessage_initializesFieldsCorrectly() throws Exception {
        Message<R> message = new TestMessage("test"); // Assuming Message is an interface
        DomainEventEntity domainEventEntity = new DomainEventEntity(message);
        assertEquals(message.getClass().getName(), domainEventEntity.getClassName());
        assertNotNull(domainEventEntity.getPayload());
        assertNotNull(domainEventEntity.getOccurredOn());
    }

    @Test
    void toMessage_withValidPayload_returnsMessage() throws Exception {
        Message<R> message = new TestMessage("test");
        DomainEventEntity domainEventEntity = new DomainEventEntity(message);
        Message<R> result = domainEventEntity.toMessage();
        assertEquals(message.getClass(), result.getClass());
    }

    @Test
    void toMessage_withInvalidPayload_throwsException() {
        DomainEventEntity domainEventEntity = new DomainEventEntity();
        domainEventEntity.setClassName("InvalidClassName");
        domainEventEntity.setPayload("InvalidPayload");
        assertThrows(Exception.class, domainEventEntity::toMessage);
    }

    record TestMessage(String value) implements Message<R> {
    }
}