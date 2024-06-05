package org.pay_my_buddy.infrastructure;

import org.pay_my_buddy.application.use_case.account.AccountSpi;
import org.pay_my_buddy.application.use_case.transaction.TransactionSpi;
import org.pay_my_buddy.application.use_case.user.UserSpi;
import org.pay_my_buddy.infrastructure.account.InMemoryAccountRepository;
import org.pay_my_buddy.infrastructure.transaction.InMemoryTransactionRepository;
import org.pay_my_buddy.infrastructure.user.InMemoryUserRepository;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class InfrastructureConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AccountSpi accountSpi() {
        return new InMemoryAccountRepository();
    }

    @Bean
    @ConditionalOnMissingBean
    public TransactionSpi transactionSpi() {
        return new InMemoryTransactionRepository();
    }

    @Bean
    @ConditionalOnMissingBean
    public UserSpi userSpi() {
        return new InMemoryUserRepository();
    }


}
