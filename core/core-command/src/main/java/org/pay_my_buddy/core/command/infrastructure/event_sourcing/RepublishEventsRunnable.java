package org.pay_my_buddy.core.command.infrastructure.event_sourcing;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.core.command.domain.event_storage.EventSourcingStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RepublishEventsRunnable {


    private final List<EventSourcingStorage<?, ?>> eventSourcingStorageList;
    @Value("${application.republishAfterStart:false}")
    private boolean republishAfterStart;

    public RepublishEventsRunnable(List<EventSourcingStorage<?, ?>> eventSourcingStorageList) {
        this.eventSourcingStorageList = eventSourcingStorageList;
    }


    @PostConstruct
    public void republishEvents() {
        log.info("Event storage list: {}", eventSourcingStorageList);
        if (republishAfterStart) {
            eventSourcingStorageList.forEach(EventSourcingStorage::republishEvents);
        }
    }
}
