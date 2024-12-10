package org.pay_my_buddy.core.framework.infrastructure.log_flow;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import static org.pay_my_buddy.core.framework.domain.log_flow.LogFlowConst.LOG_FLOW_REQUEST_ID;
import org.pay_my_buddy.core.framework.domain.log_flow.ThreadLogFlow;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogFlowInterceptor implements MethodInterceptor {

    private final LogFlowProperties logFlowProperties;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        final ThreadLogFlow.LogFlow start = LogFlowManager.startLogFlow(currentThread(), currentRequestId(), invocation);
        log(" >>> " + start.methodName() + " started - Input : " + start.input());
        Object result = null;
        try {
            result = invocation.proceed();
            return result;
        } catch (Throwable e) {
            result = e;
            throw e;
        } finally {
            final ThreadLogFlow.LogFlow stop = LogFlowManager.stopLogFlow(currentThread(), currentRequestId(), result);
            log(" <<< " + start.methodName() + " stopped - Duration : + " + stop.duration() + " ms - Output : " + stop.output());
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

    private void log(String message) {
        if (!logFlowProperties.isShowLogs()) {
            return;
        }

        switch (logFlowProperties.getLogLevel()) {
            case TRACE -> log.trace(message);
            case INFO -> log.info(message);
            case WARNING -> log.warn(message);
            case ERROR -> log.error(message);
            default -> log.debug(message);
        }
    }
}
