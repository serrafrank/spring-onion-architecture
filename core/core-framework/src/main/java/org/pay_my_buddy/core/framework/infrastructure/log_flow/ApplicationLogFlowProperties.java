package org.pay_my_buddy.core.framework.infrastructure.log_flow;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Data
@Component
@ConfigurationProperties("application.logflow")
public class ApplicationLogFlowProperties {

    private boolean isEnabled = false;
    private LOG_LEVEL logLevel = LOG_LEVEL.OFF;
    private Set<String> includePackages = new HashSet<>();
    private Set<String> excludePackages = new HashSet<>(Set.of(this.getClass().getPackageName()));

    @Getter
    @RequiredArgsConstructor
    enum LOG_LEVEL {
        OFF("OFF"),
        TRACE("TRACE"),
        INFO("INFO"),
        DEBUG("DEBUG"),
        WARNING("WARNING"),
        ERROR("ERROR");

        private final String level;
    }

    public void setExcludePackages(Set<String> excludePackages) {
       this.excludePackages.addAll(excludePackages);
    }
}
