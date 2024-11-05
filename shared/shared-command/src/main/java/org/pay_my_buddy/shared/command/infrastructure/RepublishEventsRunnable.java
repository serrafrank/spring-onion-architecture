package org.pay_my_buddy.shared.command.infrastructure;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RepublishEventsRunnable {

	@Value("${application.republishAfterStart:false}")
	private boolean republishAfterStart;

	private final List<AbstractEventSourcingHandler<?>> handlers;

	public RepublishEventsRunnable(List<AbstractEventSourcingHandler<?>> handlers) {
		this.handlers = handlers;
	}


	@PostConstruct
	public void republishEvents() {
		if (republishAfterStart) {
			log.info("Republishing events for {} handlers : {}", handlers.size() , handlers.stream().map(h -> h.getClass().getSimpleName()).toList());
			handlers.forEach(AbstractEventSourcingHandler::republishEvents);
		}
	}
}
