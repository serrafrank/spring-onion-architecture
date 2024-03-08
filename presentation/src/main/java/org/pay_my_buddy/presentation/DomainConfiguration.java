package org.pay_my_buddy.presentation;

import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.entity.commun.ApplicationService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

@Configuration
@ComponentScan(
        basePackages = {"org.pay_my_buddy.presentation", "org.pay_my_buddy.entity"},
        includeFilters = @ComponentScan.Filter(
                type = ANNOTATION,
                classes = {ApplicationService.class}
        )
)
@Slf4j
public class DomainConfiguration {

}