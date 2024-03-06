package com.example.paymybuddy.infrastructure.account;

import com.example.paymybuddy.application.account.domain.AccountAggregate;
import com.example.paymybuddy.application.account.spi.AccountRepository;
import com.example.paymybuddy.application.shared.entity.id.Id;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountRepositoryStub implements AccountRepository {

    @Override
    public boolean existsByUserId(Id userId) {
        return false;
    }

    @Override
    public Optional<AccountAggregate> findByUserId(Id userId) {
        return Optional.empty();
    }

    @Override
    public AccountAggregate save(AccountAggregate accountAggregate) {
        return null;
    }
}
