package org.pay_my_buddy.core.command.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.pay_my_buddy.core.framework.domain.MessagePublisher;
import org.pay_my_buddy.core.framework.domain.message.Message;
import org.pay_my_buddy.core.framework.infrastructure.ApplicationMessagePublisher;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@DisplayName("ApplicationMessagePublisher unit tests")
class ApplicationMessagePublisherTest {

    private ApplicationEventPublisher eventPublisher;
    private MessagePublisher messagePublisher;

    @BeforeEach
    void setUp() {
        // GIVEN: A mocked ApplicationEventPublisher and an ApplicationMessagePublisher instance
        this.eventPublisher = mock(ApplicationEventPublisher.class);
        this.messagePublisher = new ApplicationMessagePublisher(eventPublisher);
    }

    @Test
    @DisplayName("GIVEN a valid message WHEN publish is called THEN the message is published via ApplicationEventPublisher")
    void testPublishValidMessage() {
        // GIVEN
        final Message givenMessage = new TestMessage("testPayload");

        // WHEN: calling publish
        messagePublisher.publish(givenMessage);

        // THEN: verify that eventPublisher.publishEvent was called with the givenMessage
        ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);
        then(eventPublisher).should(times(1)).publishEvent(captor.capture());
        final Message capturedMessage = captor.getValue();
        assertThat(capturedMessage).isEqualTo(givenMessage);
    }

    @Test
    @DisplayName("GIVEN a null message WHEN publish is called THEN an exception is thrown")
    void testPublishNullMessage() {
        // GIVEN a null message
        final Message givenMessage = null;

        // WHEN/THEN: we expect a NullPointerException or similar since null message is not valid
        assertThatThrownBy(() -> messagePublisher.publish(givenMessage))
                .isInstanceOf(NullPointerException.class);

        // Verify that no event was published
        then(eventPublisher).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("GIVEN multiple publish calls WHEN publish is called multiple times THEN each message is published")
    void testMultiplePublishes() {
        // GIVEN multiple messages
        final Message givenMessage1 = new TestMessage("payload1");
        final Message givenMessage2 = new TestMessage("payload2");

        // WHEN: calling publish on both messages
        messagePublisher.publish(givenMessage1);
        messagePublisher.publish(givenMessage2);

        // THEN: verify both were published
        ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);
        then(eventPublisher).should(times(2)).publishEvent(captor.capture());
        assertThat(captor.getAllValues()).containsExactly(givenMessage1, givenMessage2);
    }

    @Test
    @DisplayName("GIVEN a message WHEN log is trace enabled THEN log statement occurs without affecting publish")
    void testLoggingDoesNotAffectPublish() {
        // GIVEN: a message and we assume logging is configured. We can't directly test logging here,
        // but we ensure publish still works with no issues.
        final Message givenMessage = new TestMessage("logTest");

        // WHEN
        messagePublisher.publish(givenMessage);

        // THEN
        then(eventPublisher).should(times(1)).publishEvent(givenMessage);
    }

    // Helper message class for tests
    private static final class TestMessage implements Message {
        private final String payload;

        TestMessage(String payload) {
            this.payload = payload;
        }

        @Override
        public String toString() {
            return "TestMessage{" + "payload='" + payload + '\'' + '}';
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof TestMessage other)) return false;
            return payload.equals(other.payload);
        }

        @Override
        public int hashCode() {
            return payload.hashCode();
        }
    }
}
