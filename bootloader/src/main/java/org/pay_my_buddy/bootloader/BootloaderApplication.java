package org.pay_my_buddy.bootloader;

import org.pay_my_buddy.shared.common.DomainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"org.pay_my_buddy"}
        , includeFilters = @ComponentScan.Filter(DomainService.class))
public class BootloaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootloaderApplication.class, args);
    }

}
