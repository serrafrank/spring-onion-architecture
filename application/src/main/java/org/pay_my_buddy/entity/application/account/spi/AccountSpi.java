package org.pay_my_buddy.entity.application.account.spi;

import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.account.Account;

import java.util.Optional;

public interface AccountSpi {

    boolean existsForUserId(Id userId);

    Optional<Account> findByUserId(Id userId);

    void save(Account accountEntity);

}
