package org.pay_my_buddy.shared.domain.api;


import org.pay_my_buddy.shared.domain.entity.DomainEntity;
import org.pay_my_buddy.shared.domain.entity.Id;

import java.util.Optional;

public interface DomainSpi<ENTITY extends DomainEntity, ID extends Id> {

    Optional<ENTITY> findById(Id id);

    void persist(ENTITY domainEntity);
}
