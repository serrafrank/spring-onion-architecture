package org.pay_my_buddy.presentation;

import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.entity.common.DomainService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

@AutoConfiguration
@ComponentScan(
        basePackages = {"org.pay_my_buddy"},
        includeFilters = @ComponentScan.Filter(
                type = ANNOTATION,
                classes = {DomainService.class}
        )
)
@Slf4j
public class DomainConfiguration {

}