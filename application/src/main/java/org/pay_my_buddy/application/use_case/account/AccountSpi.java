package org.pay_my_buddy.application.use_case.account;

import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.account.Account;

import java.util.Optional;

public interface AccountSpi {

    boolean existsForUserId(Id userId);

    Optional<Account> findByUserId(Id userId);

    void save(Account accountEntity);

}
