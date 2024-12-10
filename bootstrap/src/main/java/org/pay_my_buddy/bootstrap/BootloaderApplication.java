package org.pay_my_buddy.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan("org.pay_my_buddy.*")
@EnableJpaRepositories("org.pay_my_buddy.*")
@EntityScan("org.pay_my_buddy.*")
public class BootloaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootloaderApplication.class, args);
    }

}
