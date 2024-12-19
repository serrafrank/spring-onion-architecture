package org.pay_my_buddy.core.framework.infrastructure.log_flow;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.framework.domain.log_flow.LogFlowExclusion;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class LogFlowDynamicMatcherPointcut extends DynamicMethodMatcherPointcut {

    private final ApplicationLogFlowProperties properties;

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return classMatched(targetClass);
    }


    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        return classMatched(targetClass);
    }

    private boolean classMatched(Class<?> targetClass) {

        if (!properties.isEnabled()) {
            return false;
        }

        if (Stream.of(targetClass.getAnnotations()).anyMatch(annotation -> annotation instanceof LogFlowExclusion)) {
            return false;
        }

        final String targetPackageName = targetClass.getPackageName();

        if (properties.getExcludePackages().stream().anyMatch(targetPackageName::startsWith)) {
            return false;
        }

        return properties.getIncludePackages().stream().anyMatch(targetPackageName::startsWith);
    }


}
