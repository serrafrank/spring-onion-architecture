package org.pay_my_buddy.core.framework.infrastructure.log_flow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("ApplicationLogFlowProperties unit tests")
class ApplicationLogFlowPropertiesTest {

    private ApplicationLogFlowProperties properties;

    private static final String PACKAGE_NAME = ApplicationLogFlowProperties.class.getPackageName();

    @BeforeEach
    void setUp() {
        // GIVEN: A new instance of ApplicationLogFlowProperties
        this.properties = new ApplicationLogFlowProperties();
    }

    @Test
    @DisplayName("GIVEN a new instance WHEN no properties are set THEN default values are used")
    void testDefaultValues() {
        // WHEN: we check the default values
        final boolean defaultEnabled = properties.isEnabled();
        final ApplicationLogFlowProperties.LOG_LEVEL defaultLogLevel = properties.getLogLevel();
        final Set<String> defaultIncludePackages = properties.getIncludePackages();
        final Set<String> defaultExcludePackages = properties.getExcludePackages();

        // THEN: default values should match the expected defaults
        assertThat(defaultEnabled).isFalse();
        assertThat(defaultLogLevel).isEqualTo(ApplicationLogFlowProperties.LOG_LEVEL.OFF);
        assertThat(defaultIncludePackages).isEmpty();
        assertThat(defaultExcludePackages).containsExactly(PACKAGE_NAME);
    }

    @Test
    @DisplayName("GIVEN a properties instance WHEN we set values THEN the getters return those values")
    void testSetValues() {
        // GIVEN: new values
        final boolean givenEnabled = true;
        final ApplicationLogFlowProperties.LOG_LEVEL givenLogLevel = ApplicationLogFlowProperties.LOG_LEVEL.DEBUG;
        final Set<String> givenInclude = Set.of("com.example.include");
        final Set<String> givenExclude = Set.of("com.example.exclude");

        // WHEN: we set the values
        properties.setEnabled(givenEnabled);
        properties.setLogLevel(givenLogLevel);
        properties.setIncludePackages(givenInclude);
        properties.setExcludePackages(givenExclude);

        // THEN: getters return the newly set values
        assertThat(properties.isEnabled()).isEqualTo(givenEnabled);
        assertThat(properties.getLogLevel()).isEqualTo(givenLogLevel);
        assertThat(properties.getIncludePackages()).containsExactly("com.example.include");
        assertThat(properties.getExcludePackages()).containsExactly("com.example.exclude", PACKAGE_NAME);
    }

    @Test
    @DisplayName("GIVEN LOG_LEVEL enum WHEN checking values THEN correct levels are returned")
    void testLogLevelEnum() {
        // GIVEN/WHEN: directly checking the enum values

        // THEN: verify that each enum constant returns the correct level string
        assertThat(ApplicationLogFlowProperties.LOG_LEVEL.OFF.getLevel()).isEqualTo("OFF");
        assertThat(ApplicationLogFlowProperties.LOG_LEVEL.TRACE.getLevel()).isEqualTo("TRACE");
        assertThat(ApplicationLogFlowProperties.LOG_LEVEL.INFO.getLevel()).isEqualTo("INFO");
        assertThat(ApplicationLogFlowProperties.LOG_LEVEL.DEBUG.getLevel()).isEqualTo("DEBUG");
        assertThat(ApplicationLogFlowProperties.LOG_LEVEL.WARNING.getLevel()).isEqualTo("WARNING");
        assertThat(ApplicationLogFlowProperties.LOG_LEVEL.ERROR.getLevel()).isEqualTo("ERROR");
    }
}
