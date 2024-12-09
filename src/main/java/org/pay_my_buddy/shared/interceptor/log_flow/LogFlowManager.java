package org.pay_my_buddy.shared.interceptor.log_flow;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

@LogFlowExclusion
@Slf4j
public class LogFlowManager {

    static final List<ThreadLogFlow> threadFlow = new ArrayList<>();

    private LogFlowManager() {
    }

    public static void startLogFlow(Thread thread, String requestId, MethodInvocation joinPoint) {
        LogFlowManager.getThreadLogFlow(thread, requestId).startLogFlow(joinPoint);
    }


    public static void stopLogFlow(Thread thread, String requestId, Object output) {
        LogFlowManager.getThreadLogFlow(thread, requestId).stopLogFlow(output);
    }

    private static ThreadLogFlow getThreadLogFlow(Thread thread, String requestId) {
        if (findThreadLogFlow(thread, requestId).isEmpty()) {
            createThreadLogFlow(thread, requestId);
        }
        return findThreadLogFlow(thread, requestId).get();
    }

    private static Optional<ThreadLogFlow> findThreadLogFlow(Thread thread, String requestId) {
        return threadFlow.stream()
                .filter(threadLogFlow -> threadLogFlow.isThread(thread))
                .filter(threadLogFlow -> threadLogFlow.isRequestId(requestId))
                .findFirst();
    }

    private static void createThreadLogFlow(Thread thread, String requestId) {
        if (findThreadLogFlow(thread, requestId).isEmpty()) {
            threadFlow.add(new ThreadLogFlow(thread, requestId));
        }
    }


    public static List<ThreadLogFlow> getThreadLocal() {
        return threadFlow;
    }

}