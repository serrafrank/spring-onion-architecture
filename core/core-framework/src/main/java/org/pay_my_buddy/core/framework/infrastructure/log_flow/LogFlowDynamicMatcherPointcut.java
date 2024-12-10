package org.pay_my_buddy.core.framework.infrastructure.log_flow;

import java.lang.reflect.Method;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.framework.domain.log_flow.LogFlowExclusion;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogFlowDynamicMatcherPointcut extends DynamicMethodMatcherPointcut {

    private final LogFlowProperties logFlowProperties;

    private final static String LOG_FLOW_EXCLUSION_PACKAGE = LogFlowDynamicMatcherPointcut.class.getPackageName();

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return classMatched(targetClass);
    }


    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        return classMatched(targetClass);
    }

    private boolean classMatched(Class<?> targetClass) {

        if (!logFlowProperties.isEnabled()) {
            return false;
        }

        if (Stream.of(targetClass.getAnnotations()).anyMatch(annotation -> annotation instanceof LogFlowExclusion)) {
            return false;
        }

        final String targetPackageName = targetClass.getPackageName();

        if (targetPackageName.startsWith(LOG_FLOW_EXCLUSION_PACKAGE)) {
            return false;
        }

        if(logFlowProperties.getExcludePackages().stream().anyMatch(targetPackageName::startsWith)){
            return false;
        }

        return logFlowProperties.getIncludePackages().isEmpty() || logFlowProperties.getIncludePackages().stream().anyMatch(targetPackageName::startsWith);
    }


}
