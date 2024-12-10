package org.pay_my_buddy.core.framework.domain.log_flow;


import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.pay_my_buddy.core.framework.infrastructure.log_flow.LogFlowDynamicMatcherPointcut;
import org.pay_my_buddy.core.framework.infrastructure.log_flow.LogFlowInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class LogFlowConst {
    public static final String LOG_FLOW_REQUEST_ID = "ROOT_REQUEST_ID";

    @Aspect
    @Configuration
    @RequiredArgsConstructor
    public static class LogFlowAspectConfiguration {

        private final LogFlowInterceptor logFlowInterceptor;
        private final LogFlowDynamicMatcherPointcut logFlowDynamicMatcherPointcut;

        @Bean
        public Advisor performanceMonitorAdvisor() {
            return new DefaultPointcutAdvisor(logFlowDynamicMatcherPointcut, logFlowInterceptor);
        }


    }
}
