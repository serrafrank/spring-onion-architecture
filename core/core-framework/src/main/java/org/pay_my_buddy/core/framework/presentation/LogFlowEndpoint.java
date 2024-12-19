package org.pay_my_buddy.core.framework.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.pay_my_buddy.core.framework.domain.log_flow.LogFlowExclusion;
import org.pay_my_buddy.core.framework.domain.log_flow.ThreadLogFlow;
import org.pay_my_buddy.core.framework.infrastructure.log_flow.LogFlowManager;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@LogFlowExclusion
@Component
@Endpoint(id = "logflow")
@AllArgsConstructor
public class LogFlowEndpoint {


    @ReadOperation
    public ResponseEntity<?> logFlowEndpoint() throws JsonProcessingException {
        return ResponseEntity.ok(LogFlowManager.getThreadLocal().stream()
                .map(LogFlowEndpointResponse::new)
                .toList());
    }


    @ReadOperation
    public ResponseEntity<?> logFlowByRequestIdEndpoint(@Selector String requestId) throws JsonProcessingException {
        return ResponseEntity.ok(LogFlowManager.getThreadLocal(requestId).stream()
                .map(LogFlowEndpointResponse::new)
                .toList());
    }


    public record LogFlowEndpointResponse(String requestId,
                                          List<LogFlowResponse> mainMethod) {

        public LogFlowEndpointResponse(ThreadLogFlow threadLogFlow) {
            this(threadLogFlow.requestId(), threadLogFlow.logs().stream().map(LogFlowResponse::new).toList());
        }


        record LogFlowResponse(String thread,
                               String methodName,
                               LocalDateTime startTime,
                               LocalDateTime endTime,
                               Long duration,
                               List<Object> input,
                               List<LogFlowResponse> children,
                               Object output) {

            public LogFlowResponse(ThreadLogFlow.LogFlow logFlow) {
                this(logFlow.thread(),
                        logFlow.methodName(),
                        logFlow.startTime(),
                        logFlow.endTime(),
                        logFlow.duration(),
                        logFlow.input(),
                        logFlow.children().stream()
                                .map(LogFlowResponse::new)
                                .toList(),
                        logFlow.output());
            }
        }
    }
}
