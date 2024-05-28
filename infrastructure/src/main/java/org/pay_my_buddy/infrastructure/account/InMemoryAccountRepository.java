package org.pay_my_buddy.infrastructure.account;


import org.pay_my_buddy.entity.account.Account;
import org.pay_my_buddy.entity.account.spi.AccountSpi;
import org.pay_my_buddy.entity.common.entity.Id;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryAccountRepository implements AccountSpi {

    private final List<Account> accounts = new ArrayList<>();

    @Override
    public boolean existsForUserId(Id userId) {
        return
                accounts.stream()
                        .anyMatch(account -> account.getUserId().equals(userId));
    }

    @Override
    public Optional<Account> findByUserId(Id userId) {
        return
                accounts.stream()
                        .filter(account -> account.getUserId().equals(userId))
                        .reduce((a, b) -> {
                            throw new IllegalStateException("Multiple accounts found for user id: " + userId);
                        });
    }

    @Override
    public void save(Account accountEntity) {
        accounts.stream()
                .filter(account -> account.getUserId().equals(accountEntity.getUserId()))
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple accounts found for user id: " + accountEntity.getUserId());
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
