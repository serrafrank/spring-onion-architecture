package org.pay_my_buddy.shared.infrastructure;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("org.pay_my_buddy.*")
@ComponentScan(basePackages = {"org.pay_my_buddy.*"})
@EntityScan("org.pay_my_buddy.*")
public class JpaConfiguration {

}
