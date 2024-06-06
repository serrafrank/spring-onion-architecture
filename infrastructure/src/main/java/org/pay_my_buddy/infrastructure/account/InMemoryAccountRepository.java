package org.pay_my_buddy.infrastructure.account;


import org.pay_my_buddy.application.use_case.account.AccountSpi;
import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.account.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class InMemoryAccountRepository implements AccountSpi {

    private final List<Account> accounts = new ArrayList<>();

    @Override
    public boolean existsForUserId(Id userId) {
        return accounts.stream()
                .anyMatch(account -> account.userId().equals(userId));
    }

    @Override
    public Optional<Account> findByUserId(Id userId) {
        return accounts.stream()
                .filter(account -> account.userId().equals(userId))
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple accounts found for user id: " + userId);
                });
    }

    @Override
    public void save(Account accountEntity) {
        accounts.stream()
                .filter(account -> account.userId().equals(accountEntity.userId()))
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple accounts found for user id: " + accountEntity.userId());
                })
                .ifPresentOrElse(
                        account -> {
                            accounts.remove(account);
                            accounts.add(accountEntity);
                        },
                        () -> accounts.add(accountEntity)
                );
    }
}
