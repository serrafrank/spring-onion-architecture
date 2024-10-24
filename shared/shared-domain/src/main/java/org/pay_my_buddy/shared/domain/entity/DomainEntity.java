package org.pay_my_buddy.shared.domain.entity;

import org.pay_my_buddy.shared.domain.api.DomainEvent;

import java.util.List;

public interface DomainEntity {
    void addDomainEvent(DomainEvent domainEvent);

    List<DomainEvent> domainEvents();

    void clearDomainEvents();
}
