package org.pay_my_buddy.shared.presentation.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class AspectLoggingInterceptor {

    @Pointcut("execution(* org.pay_my_buddy..*.*(..))")
    private void logAll() {
    }


    @Around("logAll()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        var signature = joinPoint.getSignature();
        final var method = signature.getDeclaringTypeName() + "." + signature.getName();
        final var arguments = Arrays.toString(joinPoint.getArgs());

        var indentLog = "  ".repeat(indent());

        log.debug(indentLog + "+- " + method ) ;
        log.debug(indentLog + "| Arguments: " + arguments);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            Object result = joinPoint.proceed();
            log.debug(indentLog + "| Result: " + result);
            return result;
        } catch (Throwable e) {
            log.debug(indentLog + "| Exception thrown {} with message \"{}\"", e.getClass().getSimpleName(), e.getMessage());
            throw e;
        } finally {
            stopWatch.stop();
            log.debug(indentLog + "\\ Execution time: " + stopWatch.getTotalTimeMillis() + " ms");
        }

    }

    private int indent(){
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

}
