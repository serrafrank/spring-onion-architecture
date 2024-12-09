package org.pay_my_buddy.shared.interceptor.log_flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Aspect
@Configuration
@RequiredArgsConstructor
public class LogFlowAspectConfiguration {

    private final LogFlowInterceptor logFlowInterceptor;
    private final LogFlowDynamicMatcherPointcut logFlowDynamicMatcherPointcut;

    @Bean
    public Advisor performanceMonitorAdvisor() {
        return new DefaultPointcutAdvisor(logFlowDynamicMatcherPointcut, logFlowInterceptor);
    }


}
