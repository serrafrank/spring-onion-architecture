package org.pay_my_buddy.presentation;

import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.entity.commun.ApplicationService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

@AutoConfiguration
@ComponentScan(
        basePackages = {"org.pay_my_buddy"},
        includeFilters = @ComponentScan.Filter(
                type = ANNOTATION,
                classes = {ApplicationService.class}
        )
)
@Slf4j
public class DomainConfiguration {

}