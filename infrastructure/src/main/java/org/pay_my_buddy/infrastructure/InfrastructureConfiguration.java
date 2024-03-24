package org.pay_my_buddy.infrastructure;

import org.pay_my_buddy.entity.account.spi.AccountSpi;
import org.pay_my_buddy.entity.transaction.spi.TransactionSpi;
import org.pay_my_buddy.entity.user.spi.UserSpi;
import org.pay_my_buddy.infrastructure.account.InMemoryAccountSpi;
import org.pay_my_buddy.infrastructure.transaction.InMemoryTransactionSpi;
import org.pay_my_buddy.infrastructure.user.InMemoryUserSpi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfrastructureConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AccountSpi accountSpi() {
        return new InMemoryAccountSpi();
    }

    @Bean
    @ConditionalOnBean
    public TransactionSpi transactionSpi() {
        return new InMemoryTransactionSpi();
    }

    @Bean
    @ConditionalOnBean
    public UserSpi userSpi() {
        return new InMemoryUserSpi();
    }


}
