package org.pay_my_buddy.core.framework.infrastructure.log_flow;


import org.aopalliance.intercept.MethodInvocation;
import org.pay_my_buddy.core.framework.domain.log_flow.LogFlowExclusion;
import org.pay_my_buddy.core.framework.domain.log_flow.ThreadLogFlow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@LogFlowExclusion
public class LogFlowManager {

    static final List<ThreadLogFlow> threadFlow = new ArrayList<>();

    private LogFlowManager() {
    }

    public static ThreadLogFlow.LogFlow startLogFlow(Thread thread, String requestId, MethodInvocation methodInvocation) {
        return LogFlowManager.getThreadLogFlow(requestId).startLogFlow(thread, methodInvocation);
    }


    public static ThreadLogFlow.LogFlow stopLogFlow(Thread thread, String requestId, Object output) {
        return LogFlowManager.getThreadLogFlow(requestId).stopLogFlow(thread, output);
    }

    private static ThreadLogFlow getThreadLogFlow(String requestId) {
        if (findThreadLogFlow(requestId).isEmpty()) {
            createThreadLogFlow(requestId);
        }
        return findThreadLogFlow(requestId).get();
    }

    private static Optional<ThreadLogFlow> findThreadLogFlow(String requestId) {
        return threadFlow.stream()
                .filter(threadLogFlow -> threadLogFlow.isRequestId(requestId))
                .findFirst();
    }

    private static void createThreadLogFlow(String requestId) {
        if (findThreadLogFlow(requestId).isEmpty()) {
            threadFlow.add(new ThreadLogFlow(requestId));
        }
    }


    public static List<ThreadLogFlow> getThreadLocal() {
        return threadFlow;
    }

    public static Optional<ThreadLogFlow> getThreadLocal(String requestId) {
        return findThreadLogFlow(requestId);
    }

    public static void clearThreadLocal() {
        threadFlow.clear();
    }
}