package org.pay_my_buddy.core.framework.infrastructure.log_flow;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.core.framework.domain.log_flow.ThreadLogFlow;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for LogFlowManager
 */
@DisplayName("LogFlowManager unit tests")
class LogFlowManagerTest {

    @BeforeEach
    void setUp() {
        // Clear the threadFlow before each test to ensure isolation
        LogFlowManager.clearThreadLocal();
    }

    @Test
    @DisplayName("GIVEN a requestId WHEN startLogFlow is called THEN a new ThreadLogFlow is created and started")
    void testStartLogFlow() throws NoSuchMethodException {
        // GIVEN
        final String requestId = "testRequestId";
        final Thread currentThread = Thread.currentThread();
        final MethodInvocation mockInvocation = mockMethodInvocation();


        // WHEN
        ThreadLogFlow.LogFlow logFlow = LogFlowManager.startLogFlow(currentThread, requestId, mockInvocation);

        // THEN
        assertThat(logFlow).isNotNull();
        assertThat(logFlow.methodName()).isNotNull();
        assertThat(LogFlowManager.getThreadLocal()).hasSize(1);
        assertThat(LogFlowManager.getThreadLocal(requestId)).isPresent();
    }

    @Test
    @DisplayName("GIVEN an existing ThreadLogFlow WHEN startLogFlow is called THEN it reuses the existing instance")
    void testStartLogFlowReusesExisting() throws NoSuchMethodException {
        // GIVEN
        final String requestId = "existingRequestId";
        final Thread currentThread = Thread.currentThread();
        final MethodInvocation mockInvocation = mockMethodInvocation();

        // Create an initial ThreadLogFlow
        LogFlowManager.startLogFlow(currentThread, requestId, mockInvocation);

        // WHEN
        ThreadLogFlow.LogFlow logFlow = LogFlowManager.startLogFlow(currentThread, requestId, mockInvocation);

        // THEN
        assertThat(logFlow).isNotNull();
        assertThat(LogFlowManager.getThreadLocal()).hasSize(1); // Ensure no duplicate ThreadLogFlows
    }

    @Test
    @DisplayName("GIVEN a requestId WHEN stopLogFlow is called THEN the LogFlow is stopped and output is recorded")
    void testStopLogFlow() throws NoSuchMethodException {
        // GIVEN
        final String requestId = "stopFlowRequestId";
        final Thread currentThread = Thread.currentThread();
        final MethodInvocation mockInvocation = mockMethodInvocation();

        LogFlowManager.startLogFlow(currentThread, requestId, mockInvocation);

        final Object output = "testOutput";

        // WHEN
        ThreadLogFlow.LogFlow logFlow = LogFlowManager.stopLogFlow(currentThread, requestId, output);

        // THEN
        assertThat(logFlow).isNotNull();
        assertThat(logFlow.output()).isEqualTo(output);
    }

    @Test
    @DisplayName("GIVEN a requestId WHEN getThreadLocal is called THEN it returns the correct ThreadLogFlow")
    void testGetThreadLocalWithRequestId() throws NoSuchMethodException {
        // GIVEN
        final String requestId = "existingRequestId";
        final Thread currentThread = Thread.currentThread();
        final MethodInvocation mockInvocation = mockMethodInvocation();

        LogFlowManager.startLogFlow(currentThread, requestId, mockInvocation);

        // WHEN
        Optional<ThreadLogFlow> result = LogFlowManager.getThreadLocal(requestId);

        // THEN
        assertThat(result).isPresent();
        assertThat(result.get().requestId()).isEqualTo(requestId);
    }

    @Test
    @DisplayName("GIVEN no ThreadLogFlow exists WHEN getThreadLocal is called THEN it returns an empty optional")
    void testGetThreadLocalReturnsEmpty() {
        // GIVEN
        final String requestId = "nonExistentRequestId";

        // WHEN
        Optional<ThreadLogFlow> result = LogFlowManager.getThreadLocal(requestId);

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("GIVEN existing ThreadLogFlows WHEN clearThreadLocal is called THEN all ThreadLogFlows are removed")
    void testClearThreadLocal() throws NoSuchMethodException {
        // GIVEN
        final MethodInvocation mockInvocation = mockMethodInvocation();
        LogFlowManager.startLogFlow(Thread.currentThread(), "requestId1", mockInvocation);
        LogFlowManager.startLogFlow(Thread.currentThread(), "requestId2", mockInvocation);
        assertThat(LogFlowManager.getThreadLocal()).hasSize(2);

        // WHEN
        LogFlowManager.clearThreadLocal();

        // THEN
        assertThat(LogFlowManager.getThreadLocal()).isEmpty();
    }

    private MethodInvocation mockMethodInvocation() throws NoSuchMethodException {
        final MethodInvocation mockInvocation = mock(MethodInvocation.class);
        Method testMethod = this.getClass().getDeclaredMethod("logCallTest", String.class);
        when(mockInvocation.getMethod()).thenReturn(testMethod);
        when(mockInvocation.getArguments()).thenReturn(new Object[]{"test"});

        return mockInvocation;
    }

    public void logCallTest(String test) {
        // Test method for reflection
    }
}
