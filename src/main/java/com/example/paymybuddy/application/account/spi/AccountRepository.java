package com.example.paymybuddy.application.account.spi;

import com.example.paymybuddy.application.account.domain.AccountAggregate;
import com.example.paymybuddy.application.account.domain.AccountId;
import com.example.paymybuddy.core.common.entity.id.Id;

import java.util.Optional;

public interface AccountRepository {

    boolean existsByUserId(Id userId);

    Optional<AccountAggregate> findByUserId(Id userId);
    AccountId save(AccountAggregate accountAggregate);

}
