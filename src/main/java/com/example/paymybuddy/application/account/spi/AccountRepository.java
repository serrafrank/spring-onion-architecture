package com.example.paymybuddy.application.account.spi;

import com.example.paymybuddy.application.account.domain.AccountAggregate;
import com.example.paymybuddy.application.shared.entity.id.Id;

import java.util.Optional;

public interface AccountRepository {

    boolean existsByUserId(Id userId);

    Optional<AccountAggregate> findByUserId(Id userId);

    AccountAggregate save(AccountAggregate accountAggregate);

}
