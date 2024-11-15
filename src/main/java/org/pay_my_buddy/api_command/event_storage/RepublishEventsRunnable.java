package org.pay_my_buddy.api_command.event_storage;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.api_command.AggregateStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RepublishEventsRunnable {

	@Value("${application.republishAfterStart:false}")
	private boolean republishAfterStart;

	private final AggregateStorage<?, ?> aggregateStorage;

	public RepublishEventsRunnable(AggregateStorage<?, ?> aggregateStorage) {
		this.aggregateStorage = aggregateStorage;
	}


	@PostConstruct
	public void republishEvents() {
		if (republishAfterStart) {
			aggregateStorage.republishEvents();
		}
	}
}
