package org.pay_my_buddy.api_command;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.api_command.event_storage.AggregateEventListener;
import org.pay_my_buddy.shared.EntityId;
import org.pay_my_buddy.shared.exception.InternalErrorException;

@Slf4j
public abstract class AggregateRoot<ID extends EntityId> implements Serializable {

	@Getter
	@Accessors(fluent = true)
	protected final ID id;

	private final List<EventWrapper> uncommitedChanges = new ArrayList<>();
	private final List<EventWrapper> commitedChanges = new ArrayList<>();

	protected AggregateRoot(ID id) {
		this.id = id;
	}
	

	public int version() {
		return this.uncommitedChanges.size() + this.commitedChanges.size();
	}

	public void commitChanges() {
		commitedChanges.addAll(uncommitedChanges);
		uncommitedChanges.clear();
	}

	public void rollbackChanges() {
		uncommitedChanges.clear();
		commitedChanges.forEach(e -> applyChanges(e.event()));
	}

	public void addEvent(Event event) {
		var wrappedEvent = new EventWrapper(event, uncommitedChanges.size());
		applyChanges(event);
		uncommitedChanges.add(wrappedEvent);
	}

	public List<Event> commitedChanges() {
		return commitedChanges.stream().map(EventWrapper::event).toList();
	}

	public List<Event> uncommitedChanges() {
		return uncommitedChanges.stream().map(EventWrapper::event).toList();
	}

	public void replayEvents() {
		commitedChanges
				.stream()
				.sorted(Comparator.comparing(EventWrapper::index))
				.forEach(e -> applyChanges(e.event()));
	}

	private  <EVENT extends Event> void applyChanges(EVENT event) {
		Arrays.stream(this.getClass().getDeclaredMethods())
				.filter(method -> method.isAnnotationPresent(AggregateEventListener.class))
				.filter(method -> method.getParameterTypes()[0].isAssignableFrom(event.getClass()))
				.reduce((a, b) -> { throw new InternalErrorException("Multiple event handlers found for event type: " + event.getClass());} )
				.ifPresentOrElse(method -> {
					invokeEventHandlerMethod(event, method);
				}, () -> {
					throw new IllegalArgumentException("No handler registered for event type: " + event.getClass());
				});
	}

	private <EVENT extends Event> void invokeEventHandlerMethod(EVENT event, Method method) {
		try {
			method.invoke(this, event);
		} catch (Exception e) {
			throw new IllegalArgumentException("Error applying event", e);
		}
	}

	record EventWrapper(
			EventId eventId,
			LocalDateTime timestamp,
			int index,
			String eventType,
			String eventData) {

		private final static JsonMapper MAPPER = JsonMapper.builder()
				.addModule(new JavaTimeModule())
				.visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
				.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
				.build();

		public EventWrapper(Event event, int index) {
			this(event.eventId(), LocalDateTime.now(Clock.systemUTC()), index, event.getClass().getName(), serialize(event));
		}

		public Event event() {
			try {
				var aggregateClass = Class.forName(eventType);
				return (Event) MAPPER.readValue(eventData, aggregateClass);
			} catch (Exception e) {
				throw new InternalErrorException(e.getMessage(), e);
			}
		}

		private static String serialize(Event aggregate) {
			try {
				return MAPPER.writeValueAsString(aggregate);
			} catch (Exception e) {
				throw new InternalErrorException(e.getMessage(), e);
			}
		}
	}
}

