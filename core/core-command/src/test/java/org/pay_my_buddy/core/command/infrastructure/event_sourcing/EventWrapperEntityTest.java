package org.pay_my_buddy.core.command.infrastructure.event_sourcing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.core.command.domain.ObjectConverter;
import org.pay_my_buddy.core.command.domain.event_storage.EventWrapper;
import org.pay_my_buddy.core.framework.domain.message.Event;
import org.pay_my_buddy.core.framework.domain.message.EventId;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("EventWrapperEntity unit tests")
class EventWrapperEntityTest {

    private Event testEvent;
    private EventWrapper testEventWrapper;

    @BeforeEach
    void setUp() {
        // GIVEN: a test event and a test EventWrapper
        final String givenData = "testData";
        this.testEvent = new TestEvent(givenData);

        this.testEventWrapper = new EventWrapper() {
            final EntityId aggregateId = EntityId.of(EntityId.generateId());
            @Override
            public EventId eventId() {
                return testEvent.eventId();
            }

            @Override
            public LocalDateTime timestamp() {
                return LocalDateTime.of(2020, 1, 1, 12, 0);
            }

            @Override
            public int index() {
                return 42;
            }

            @Override
            public EntityId aggregateId() {
                return aggregateId;
            }

            @Override
            public String aggregateType() {
                return "MyAggregateType";
            }

            @Override
            public String eventType() {
                return testEvent.getClass().getName();
            }

            @Override
            public Event event() {
                return testEvent;
            }
        };
    }

    @Test
    @DisplayName("GIVEN an EventWrapper WHEN creating EventWrapperEntity from it THEN fields are correctly set")
    void testConstructorFromEventWrapper() {
        // GIVEN: a test eventWrapper (setup in @BeforeEach)

        // WHEN: we create a new EventWrapperEntity from the eventWrapper
        final EventWrapperEntity entity = new EventWrapperEntity(testEventWrapper);

        // THEN: all fields should match the event wrapper
        assertThat(entity.eventId()).isEqualTo(testEvent.eventId());
        assertThat(entity.timestamp()).isEqualTo(testEventWrapper.timestamp());
        assertThat(entity.index()).isEqualTo(testEventWrapper.index());
        assertThat(entity.aggregateId().value()).isEqualTo(testEventWrapper.aggregateId().value());
        assertThat(entity.aggregateType()).isEqualTo("MyAggregateType");
        assertThat(entity.eventType()).isEqualTo(testEvent.getClass().getName());
        assertThat(entity.event()).isInstanceOf(TestEvent.class);
        assertThat(((TestEvent) entity.event()).data()).isEqualTo("testData");
    }

    @Test
    @DisplayName("GIVEN an EventWrapperEntity with fields set WHEN getting fields THEN all getters return correct values")
    void testGettersAfterManualSet() {
        // GIVEN: a manually created EventWrapperEntity
        final EventId eventId = new EventId();
        final EventWrapperEntity entity = new EventWrapperEntity();
        final String givenEventId = eventId.value();
        final LocalDateTime givenTimestamp = LocalDateTime.of(2021, 5, 10, 14, 30);
        final int givenIndex = 10;
        final String givenAggId = "aggIdXYZ";
        final String givenAggType = "SomeAggregate";
        final String givenEventType = TestEvent.class.getName();
        final TestEvent givenEvent = new TestEvent("someData");

        entity.eventId(givenEventId);
        entity.timestamp(givenTimestamp);
        entity.index(givenIndex);
        entity.aggregateId(givenAggId);
        entity.aggregateType(givenAggType);
        entity.eventType(givenEventType);
        entity.eventData(ObjectConverter.toJson(givenEvent));

        // WHEN: we call getters
        final EventId resultEventId = entity.eventId();
        final LocalDateTime resultTimestamp = entity.timestamp();
        final int resultIndex = entity.index();
        final EntityId resultAggId = entity.aggregateId();
        final String resultAggType = entity.aggregateType();
        final String resultEventType = entity.eventType();
        final Event resultEvent = entity.event();

        // THEN: values should match what we set
        assertThat(resultEventId.value()).isEqualTo(givenEventId);
        assertThat(resultTimestamp).isEqualTo(givenTimestamp);
        assertThat(resultIndex).isEqualTo(givenIndex);
        assertThat(resultAggId.value()).isEqualTo(givenAggId);
        assertThat(resultAggType).isEqualTo(givenAggType);
        assertThat(resultEventType).isEqualTo(givenEventType);
        assertThat(resultEvent).isInstanceOf(TestEvent.class);
        assertThat(((TestEvent) resultEvent).data()).isEqualTo("someData");
    }

    @Test
    @DisplayName("GIVEN an EventWrapperEntity WHEN eventData is invalid THEN deserialization should fail")
    void testInvalidEventData() {
        // GIVEN: an entity with an invalid JSON
        final EventWrapperEntity entity = new EventWrapperEntity();
        entity.eventData("not a valid json");
        entity.eventType(TestEvent.class.getName());

        // WHEN/THEN: objectConverter.fromJson should fail inside event()
        // Here we rely on ObjectConverter to throw a runtime exception on invalid JSON
        assertThatThrownBy(entity::event)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("GIVEN an EventWrapperEntity WHEN eventType does not correspond to a known event class THEN deserialization fails")
    void testUnknownEventType() {
        // GIVEN: an entity with a non-existing class as eventType
        final EventWrapperEntity entity = new EventWrapperEntity();
        entity.eventData("{}");
        entity.eventType("com.unknown.NonExistingEventClass");

        // WHEN/THEN: Trying to deserialize should fail
        assertThatThrownBy(entity::event)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("GIVEN a normal scenario WHEN calling all methods multiple times THEN returns consistent results")
    void testIdempotency() {
        // GIVEN: Construct a valid entity from the testEventWrapper
        final EventWrapperEntity entity = new EventWrapperEntity(testEventWrapper);

        // WHEN: call getters multiple times
        final EventId firstEventIdCall = entity.eventId();
        final EventId secondEventIdCall = entity.eventId();

        // THEN: results are consistent (idempotent)
        assertThat(firstEventIdCall).isEqualTo(secondEventIdCall);

        // Just a sanity check for consistent behavior
    }


    record TestEvent(EventId eventId, String data) implements Event{
        public TestEvent(String data) {
            this( new EventId(), data);
        }
    }

}
