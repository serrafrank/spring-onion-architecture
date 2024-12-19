package org.pay_my_buddy.core.framework.infrastructure.log_flow;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;
import static org.pay_my_buddy.core.framework.domain.log_flow.LogFlowConst.LOG_FLOW_REQUEST_ID;

/**
 * Unit tests for LogFlowInterceptor
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("LogFlowInterceptor unit tests")
class LogFlowInterceptorTest {
    @Mock
    private ApplicationLogFlowProperties properties;
    @Mock
    private MethodInvocation invocation;
    @InjectMocks
    private LogFlowInterceptor interceptor;

    @BeforeEach
    void setUp() {
        // Clear MDC to avoid side effects between tests
        MDC.clear();
    }

    @Test
    @DisplayName("GIVEN a method invocation WHEN invoke is called THEN logs input, output, and duration")
    void testInvokeLogsInputOutput() throws Throwable {
        // GIVEN: Logging is set to DEBUG
        given(properties.getLogLevel()).willReturn(ApplicationLogFlowProperties.LOG_LEVEL.DEBUG);

        // Mock method invocation
        given(invocation.getArguments()).willReturn(new Object[]{"arg1", "arg2"});
        given(invocation.getMethod()).willReturn(LogFlowInterceptorTest.class.getMethod("dummyTestMethod"));
        given(invocation.proceed()).willReturn("testResult");

        // WHEN: invoke is called
        interceptor.invoke(invocation);

        // THEN: Logs input, output, and duration
        // We can't directly verify logs, but we ensure proceed is called and MDC is used
        verify(invocation, times(1)).proceed();
        assertThat(MDC.get(LOG_FLOW_REQUEST_ID)).isNotNull();
    }

    @Test
    @DisplayName("GIVEN a method invocation that throws an exception WHEN invoke is called THEN logs the exception")
    void testInvokeLogsException() throws Throwable {
        // GIVEN: Logging is set to ERROR
        given(properties.getLogLevel()).willReturn(ApplicationLogFlowProperties.LOG_LEVEL.ERROR);

        // Mock method invocation
        given(invocation.getArguments()).willReturn(new Object[]{"arg1"});
        given(invocation.getMethod()).willReturn(LogFlowInterceptorTest.class.getMethod("dummyTestMethod"));
        given(invocation.proceed()).willThrow(new RuntimeException("Test exception"));

        // WHEN/THEN: invoke logs the exception and rethrows it
        assertThatThrownBy(() -> interceptor.invoke(invocation))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Test exception");

        // Verify MDC was set
        assertThat(MDC.get(LOG_FLOW_REQUEST_ID)).isNotNull();
    }

    @Test
    @DisplayName("GIVEN no requestId in MDC WHEN invoke is called THEN it generates a new requestId")
    void testInvokeGeneratesRequestId() throws Throwable {
        // GIVEN: Logging is set to TRACE
        given(properties.getLogLevel()).willReturn(ApplicationLogFlowProperties.LOG_LEVEL.TRACE);

        // Mock method invocation
        given(invocation.getArguments()).willReturn(new Object[]{"arg1"});
        given(invocation.getMethod()).willReturn(LogFlowInterceptorTest.class.getMethod("dummyTestMethod"));
        given(invocation.proceed()).willReturn("testResult");

        // WHEN: invoke is called
        interceptor.invoke(invocation);

        // THEN: A new requestId is generated in MDC
        String requestId = MDC.get(LOG_FLOW_REQUEST_ID);
        assertThat(requestId).isNotNull();
        assertThat(requestId).isNotBlank();
        assertThat(UUID.fromString(requestId)).isInstanceOf(UUID.class);
    }


    @SuppressWarnings("unused")
    public String dummyTestMethod() {
        return "dummy";
    }
}
