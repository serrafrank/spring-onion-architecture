package org.pay_my_buddy.shared.interceptor.log_flow;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.aopalliance.intercept.MethodInvocation;


@LogFlowExclusion
@Getter
@Accessors(fluent = true)
public class ThreadLogFlow {

    private final String thread;
    private final String requestId;
    private final Deque<LogFlow> stack = new ArrayDeque<>();
    private LogFlow log;


    public ThreadLogFlow(Thread thread, String requestId) {
        this.thread = getThreadId(thread);
        this.requestId = requestId;
    }

    private static String getThreadId(Thread thread) {
        return thread.toString();
    }

    public boolean isThread(Thread thread) {
        return this.thread.equals(getThreadId(thread));
    }

    public boolean isRequestId(String requestId) {
        return this.requestId.equals(requestId);
    }

    public void startLogFlow(MethodInvocation methodInvocation) {
        LogFlow logFlow = new LogFlow(methodInvocation);
        stack.push(logFlow);
    }

    public void stopLogFlow(Object output) {
        LogFlow childLog = stack.pop();
        childLog.stop(output);

        // If the stack is empty after the pop, this is the first log, and therefore the root method called.
        // Otherwise, it is added as a child of the last element
        if (stack.isEmpty()) {
            this.log = childLog;
        } else {
            stack.peek().addChild(childLog);
        }
    }

    @Getter
    @Accessors(fluent = true)
    public static class LogFlow {
        private final String methodName;
        private final List<Object> input;
        private final List<LogFlow> children = new ArrayList<>();
        private final LocalDateTime startTime = LocalDateTime.now();
        private LocalDateTime endTime;
        private Object output;

        public LogFlow(MethodInvocation methodInvocation) {
            this.methodName =  methodInvocation.getMethod().getName();
            this.input = Stream.of(methodInvocation.getMethod().getParameters())
                    .map(this::copy)
                    .toList();
        }

        public void stop(Object output) {
            this.endTime = LocalDateTime.now();
            this.output = copy(output);
        }

        private Object copy(Object object) {
            return object != null ? object.toString() : null;
        }

        public void addChild(LogFlow child) {
            children.add(child);
        }

        public long duration() {
            if (endTime == null) {
                return 0;
            }
            return endTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }

    }
}