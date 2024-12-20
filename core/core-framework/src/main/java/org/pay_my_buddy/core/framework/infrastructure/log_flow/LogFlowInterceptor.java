package org.pay_my_buddy.core.framework.infrastructure.log_flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.pay_my_buddy.core.framework.domain.log_flow.ThreadLogFlow;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.pay_my_buddy.core.framework.domain.log_flow.LogFlowConst.LOG_FLOW_REQUEST_ID;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogFlowInterceptor implements MethodInterceptor {

    private final ApplicationLogFlowProperties properties;

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

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        final ThreadLogFlow.LogFlow start = LogFlowManager.startLogFlow(currentThread(), currentRequestId(), invocation);

        String indent = "\t".repeat(start.depth());
        log(indent + "/+ " + start.methodName());
        log(indent + "|- Input : " + start.input());

        Object result = null;
        try {
            result = invocation.proceed();
            return result;
        } catch (Throwable e) {
            result = e;
            throw e;
        } finally {
            final ThreadLogFlow.LogFlow stop = LogFlowManager.stopLogFlow(currentThread(), currentRequestId(), result);

            if (stop.endedWithException()) {
                log(indent + "|- Exception : " + stop.output());
            } else {
                log(indent + "|- Output : " + stop.output());
            }

            log(indent + "\\_ Duration : " + stop.duration() + " ms");
        }
    }

    private void log(String message) {
        if (properties.getLogLevel() == ApplicationLogFlowProperties.LOG_LEVEL.OFF) {
            return;
        }

        switch (properties.getLogLevel()) {
            case TRACE -> log.trace(message);
            case DEBUG -> log.debug(message);
            case INFO -> log.info(message);
            case WARNING -> log.warn(message);
            case ERROR -> log.error(message);
            default -> throw new IllegalArgumentException("Unknown log level: " + properties.getLogLevel());
        }
    }
}
