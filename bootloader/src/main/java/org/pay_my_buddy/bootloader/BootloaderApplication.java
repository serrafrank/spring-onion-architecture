package org.pay_my_buddy.bootloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"org.pay_my_buddy.presentation", "org.pay_my_buddy.infrastructure"})
public class BootloaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootloaderApplication.class, args);
    }

}
