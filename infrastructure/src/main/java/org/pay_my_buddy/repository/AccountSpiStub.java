package org.pay_my_buddy.repository;


import org.pay_my_buddy.entity.account.Account;
import org.pay_my_buddy.entity.account.spi.AccountSpi;
import org.pay_my_buddy.entity.commun.entity.Id;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountSpiStub implements AccountSpi {

    @Override
    public boolean existsForUserId(Id userId) {
        return false;
    }

    @Override
    public Optional<Account> findByUserId(Id userId) {
        return Optional.empty();
    }

    @Override
    public Account save(Account accountEntity) {
        return null;
    }
}
