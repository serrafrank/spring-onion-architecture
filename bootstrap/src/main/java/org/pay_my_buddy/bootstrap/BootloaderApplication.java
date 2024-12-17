package org.pay_my_buddy.bootstrap;

import org.pay_my_buddy.core.framework.domain.DomainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = BootloaderApplication.APPLICATION_ROOT_PACKAGE,
        includeFilters = @ComponentScan.Filter(DomainService.class))
@EnableJpaRepositories(BootloaderApplication.APPLICATION_ROOT_PACKAGE)
@EntityScan(BootloaderApplication.APPLICATION_ROOT_PACKAGE)
@ConfigurationPropertiesScan(BootloaderApplication.APPLICATION_ROOT_PACKAGE)
public class BootloaderApplication {

    public static final String APPLICATION_ROOT_PACKAGE = "org.pay_my_buddy";

    public static void main(String[] args) {
        SpringApplication.run(BootloaderApplication.class, args);
    }

}
