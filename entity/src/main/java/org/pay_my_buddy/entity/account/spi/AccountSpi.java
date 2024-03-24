package org.pay_my_buddy.entity.account.spi;

import org.pay_my_buddy.entity.account.Account;
import org.pay_my_buddy.entity.commun.entity.Id;

import java.util.Optional;

public interface AccountSpi {

    boolean existsForUserId(Id userId);

    Optional<Account> findByUserId(Id userId);

    void save(Account accountEntity);

}
