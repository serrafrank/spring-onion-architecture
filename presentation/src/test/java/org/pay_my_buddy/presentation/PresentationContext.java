package org.pay_my_buddy.presentation;

import com.tngtech.jgiven.integration.spring.EnableJGiven;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@EnableJGiven
@ComponentScan({"org.pay_my_buddy.presentation", "org.pay_my_buddy.infrastructure"})
public class PresentationContext {
}
