package org.pay_my_buddy.core.framework.domain.log_flow;


import lombok.Getter;
import lombok.experimental.Accessors;
import org.aopalliance.intercept.MethodInvocation;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Stream;


@LogFlowExclusion
@Getter
@Accessors(fluent = true)
public class ThreadLogFlow {

    private final String requestId;

    private final Map<String, Deque<LogFlow>> stack = new HashMap<>();

    private final List<LogFlow> logs = new ArrayList<>();


    public ThreadLogFlow(String requestId) {
        this.requestId = requestId;
    }

    private static String getThreadId(Thread thread) {
        return thread.toString();
    }


    public boolean isRequestId(String requestId) {
        return this.requestId.equals(requestId);
    }

    public LogFlow startLogFlow(Thread thread, MethodInvocation methodInvocation) {
        String currentThread = getThreadId(thread);

        if (!stack.containsKey(currentThread)) {
            stack.put(currentThread, new ArrayDeque<>());
        }
        LogFlow logFlow = new LogFlow(currentThread, methodInvocation, stack.get(currentThread).size());
        stack.get(currentThread).push(logFlow);

        return logFlow;
    }

    public LogFlow stopLogFlow(Thread thread, Object output) {
        String currentThread = getThreadId(thread);
        LogFlow childLog = this.stack.get(currentThread).pop();
        childLog.stop(output);

        // If the stack is empty after the pop, this is the first log, and therefore the root method called.
        // Otherwise, it is added as a child of the last element
        if (this.stack.get(currentThread).isEmpty()) {
            this.logs.add(childLog);
        } else {
            Objects.requireNonNull(this.stack.get(currentThread).peek()).addChild(childLog);
        }

        return childLog;
    }

    @Getter
    @Accessors(fluent = true)
    public static class LogFlow {
        private final String thread;
        private final String methodName;
        private final Integer depth;
        private final List<Object> input;
        private final List<LogFlow> children = new ArrayList<>();
        private final LocalDateTime startTime = LocalDateTime.now();
        private LocalDateTime endTime;
        private Object output;
        private boolean endedWithException = false;

        public LogFlow(String thread, MethodInvocation methodInvocation, Integer depth) {
            this.thread = thread;
            this.methodName = methodInvocation.getMethod().getDeclaringClass().getName() + "." + methodInvocation.getMethod().getName() + "(" + Stream.of(methodInvocation.getMethod().getParameters()).map(parameter -> parameter.getType().getSimpleName()).reduce((a, b) -> a + ", " + b).orElse("") + ")";
            this.input = Stream.of(methodInvocation.getArguments())
                    .map(this::copy)
                    .toList();
            this.depth = depth;
        }

        public void stop(Object output) {
            this.endTime = LocalDateTime.now();
            this.output = copy(output);
            this.endedWithException = output instanceof Throwable;
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