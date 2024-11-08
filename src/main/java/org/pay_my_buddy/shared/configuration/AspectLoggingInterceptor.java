package org.pay_my_buddy.shared.configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class AspectLoggingInterceptor {

    @Value("${application.enableAspectLogging:false}")
    private boolean enableAspect;

    @Pointcut("execution(* org.pay_my_buddy..*.*(..))")
    private void logAll() {
    }


    @Around("logAll()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        var signature = joinPoint.getSignature();
        final var method = signature.getDeclaringTypeName() + "." + signature.getName();
        final var arguments = Arrays.toString(joinPoint.getArgs());

        var indentLog = "  ".repeat(indent());

        log("{}+- {}", indentLog, method);
        log("{}| Arguments: {}", indentLog, arguments);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            Object result = joinPoint.proceed();
            log("{}| Result: {}", indentLog, result);
            return result;
        } catch (Throwable e) {
            log("{}| Exception thrown {} with message \"{}\"", indentLog, e.getClass().getSimpleName(), e.getMessage());
            throw e;
        } finally {
            stopWatch.stop();
            log("{}\\ Execution time: {} ms", indentLog, stopWatch.getTotalTimeMillis());
        }

    }

    private int indent() {
        final String[] namespaces = this.getClass().getPackageName()
                .split("\\.");

        final String namespace = namespaces[0] + "." + namespaces[1];

        return (int) Arrays.stream(Thread.currentThread().getStackTrace())
                .map(StackTraceElement::getClassName)
                .filter(s -> s.contains(namespace))
                .filter(s -> !s.contains(this.getClass().getName()))
                .skip(1)
                .count();
    }

    private void log(String message, Object... args) {
        if (enableAspect) {
            log.debug(message, args);
        }
    }

}
