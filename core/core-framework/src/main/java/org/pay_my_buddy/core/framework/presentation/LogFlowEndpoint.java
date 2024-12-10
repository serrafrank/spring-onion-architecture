package org.pay_my_buddy.core.framework.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.pay_my_buddy.core.framework.domain.log_flow.LogFlowExclusion;
import org.pay_my_buddy.core.framework.infrastructure.log_flow.LogFlowManager;
import org.pay_my_buddy.core.framework.domain.log_flow.ThreadLogFlow;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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


    public record LogFlowEndpointResponse(String thread,
                                          String requestId,
                                          LogFlowResponse mainMethod) {

        public LogFlowEndpointResponse(ThreadLogFlow threadLogFlow) {
            this(threadLogFlow.thread(), threadLogFlow.requestId(), new LogFlowResponse(threadLogFlow.log()));
        }


        record LogFlowResponse(String methodName,
                               LocalDateTime startTime,
                               LocalDateTime endTime,
                               Long duration,
                               List<Object> input,
                               List<LogFlowResponse> children,
                               Object output) {

            public LogFlowResponse(ThreadLogFlow.LogFlow logFlow) {
                this(logFlow.methodName(),
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
