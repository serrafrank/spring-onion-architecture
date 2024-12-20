package org.pay_my_buddy.core.framework.infrastructure.log_flow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pay_my_buddy.core.framework.domain.log_flow.LogFlowExclusion;

import java.lang.reflect.Method;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Unit tests for LogFlowDynamicMatcherPointcut
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("LogFlowDynamicMatcherPointcut unit tests")
class LogFlowDynamicMatcherPointcutTest {

    @Mock
    private ApplicationLogFlowProperties properties;

    @InjectMocks
    private LogFlowDynamicMatcherPointcut pointcut;

    @BeforeEach
    void setUp() {
        // GIVEN: Mocked ApplicationLogFlowProperties and a new LogFlowDynamicMatcherPointcut instance
        this.properties = Mockito.mock(ApplicationLogFlowProperties.class);
        this.pointcut = new LogFlowDynamicMatcherPointcut(properties);
    }

    @Test
    @DisplayName("GIVEN logging is disabled WHEN matches is called THEN it returns false")
    void testMatchesWhenLoggingDisabled() throws NoSuchMethodException {
        // GIVEN: Logging is disabled
        given(properties.isEnabled()).willReturn(false);

        final Method method = TestClass.class.getMethod("testMethod");

        // WHEN: matches is called
        final boolean result = pointcut.matches(method, TestClass.class);

        // THEN: it should return false
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("GIVEN class has LogFlowExclusion annotation WHEN matches is called THEN it returns false")
    void testMatchesWithLogFlowExclusionAnnotation() throws NoSuchMethodException {
        // GIVEN: Logging is enabled, and the class has @LogFlowExclusion
        given(properties.isEnabled()).willReturn(true);

        final Method method = ExcludedClass.class.getMethod("excludedMethod");

        // WHEN: matches is called
        final boolean result = pointcut.matches(method, ExcludedClass.class);

        // THEN: it should return false
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("GIVEN class is in excluded package WHEN matches is called THEN it returns false")
    void testMatchesWithExcludedPackage() throws NoSuchMethodException {
        // GIVEN: Logging is enabled and the class belongs to an excluded package
        given(properties.isEnabled()).willReturn(true);
        given(properties.getExcludePackages()).willReturn(Set.of(LogFlowDynamicMatcherPointcut.class.getPackageName()));

        final Method method = TestClass.class.getMethod("testMethod");

        // WHEN: matches is called
        final boolean result = pointcut.matches(method, TestClass.class);

        // THEN: it should return false
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("GIVEN class is in included package WHEN matches is called THEN it returns true")
    void testMatchesWithIncludedPackage() throws NoSuchMethodException {
        // GIVEN: Logging is enabled and the class belongs to an included package
        given(properties.isEnabled()).willReturn(true);
        given(properties.getIncludePackages()).willReturn(Set.of("org.pay_my_buddy.core.framework.infrastructure"));

        final Method method = TestClass.class.getMethod("testMethod");

        // WHEN: matches is called
        final boolean result = pointcut.matches(method, TestClass.class);

        // THEN: it should return true
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("GIVEN no packages are included or excluded WHEN matches is called THEN it returns false")
    void testMatchesWithNoIncludeOrExcludePackages() throws NoSuchMethodException {
        // GIVEN: Logging is enabled and no packages are explicitly included or excluded
        given(properties.isEnabled()).willReturn(true);
        given(properties.getIncludePackages()).willReturn(Set.of());
        given(properties.getExcludePackages()).willReturn(Set.of());

        final Method method = TestClass.class.getMethod("testMethod");

        // WHEN: matches is called
        final boolean result = pointcut.matches(method, TestClass.class);

        // THEN: it should return true
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("GIVEN class is in log flow exclusion package WHEN matches is called THEN it returns false")
    void testMatchesWithLogFlowExclusionPackage() throws NoSuchMethodException {
        // GIVEN: Logging is enabled and the class belongs to the exclusion package
        given(properties.isEnabled()).willReturn(true);
        given(properties.getExcludePackages()).willReturn(Set.of(LogFlowDynamicMatcherPointcut.class.getPackageName()));

        final Method method = LogFlowDynamicMatcherPointcut.class.getMethod("matches", Method.class, Class.class);

        // WHEN: matches is called
        final boolean result = pointcut.matches(method, LogFlowDynamicMatcherPointcut.class);

        // THEN: it should return false
        assertThat(result).isFalse();
    }

    // --- Helper classes for tests --- //
    static class TestClass {
        public void testMethod() {
        }
    }

    @LogFlowExclusion
    static class ExcludedClass {
        public void excludedMethod() {
        }
    }
}
