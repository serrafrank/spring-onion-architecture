package org.pay_my_buddy.api_command.event_storage;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.api_command.EventSourcingStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RepublishEventsRunnable {

	@Value("${application.republishAfterStart:false}")
	private boolean republishAfterStart;

	private final List<EventSourcingStorage<?, ?>> eventSourcingStorageList;

	public RepublishEventsRunnable(List<EventSourcingStorage<?, ?>> eventSourcingStorageList) {
		this.eventSourcingStorageList = eventSourcingStorageList;
	}


	@PostConstruct
	public void republishEvents() {
		if (republishAfterStart) {
			eventSourcingStorageList.forEach(EventSourcingStorage::republishEvents);
		}
	}
}
