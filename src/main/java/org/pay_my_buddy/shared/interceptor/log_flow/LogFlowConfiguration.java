package org.pay_my_buddy.shared.interceptor.log_flow;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.Aspect;
import org.pay_my_buddy.shared.exception.ApiExceptionInterceptor;
import static org.pay_my_buddy.shared.interceptor.log_flow.LogFlowConst.LOG_FLOW_REQUEST_ID;
import org.slf4j.MDC;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Aspect
@Configuration
public class LogFlowConfiguration {

    @Bean
    LogFlowInterceptor LogFlowInterceptor() {
        return new LogFlowInterceptor();
    }

    @Bean
    public Advisor performanceMonitorAdvisor() {
        LogFlowDynamicMatcherPointcut pointcut = new LogFlowDynamicMatcherPointcut();
        return new DefaultPointcutAdvisor(pointcut, LogFlowInterceptor());
    }


    static class LogFlowDynamicMatcherPointcut extends DynamicMethodMatcherPointcut {

        private static final String CURRENT_PACKAGE = ApiExceptionInterceptor.class.getPackageName();
        private static final String ROOT_PACKAGE = CURRENT_PACKAGE.split("\\.")[0] + "." + CURRENT_PACKAGE.split("\\.")[1];


        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return classMatched(targetClass);
        }


        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return classMatched(targetClass);
        }

        private static boolean classMatched(Class<?> targetClass) {
            return targetClass.getPackageName().startsWith(ROOT_PACKAGE)
                    && !targetClass.getPackageName().startsWith(CURRENT_PACKAGE)
                    && Stream.of(targetClass.getAnnotations()).noneMatch(annotation -> annotation instanceof LogFlowExclusion);
        }
    }

    static class LogFlowInterceptor implements MethodInterceptor {

        public LogFlowInterceptor() {
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            LogFlowManager.startLogFlow(currentThread(), currentRequestId(), invocation);
            Object result = null;
            try {
                result = invocation.proceed();
                return result;
            } catch (Throwable e) {
                result = e;
                throw e;
            } finally {
                LogFlowManager.stopLogFlow(currentThread(), currentRequestId(), result);
            }
        }


        private static String currentRequestId() {
            String requestId = MDC.get(LOG_FLOW_REQUEST_ID);
            if (requestId == null) {
                requestId = UUID.randomUUID().toString();
                MDC.put(LOG_FLOW_REQUEST_ID, requestId);
            }

            return requestId;
        }

        private static Thread currentThread() {
            return Thread.currentThread();
        }
    }

}
