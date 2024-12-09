package org.pay_my_buddy.shared.interceptor.log_flow;

import java.util.Set;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("application.logflow")
public class LogFlowProperties {

    private boolean isEnabled = false;
    private boolean showLogs = false;
    private LOG_LEVEL logLevel = LOG_LEVEL.DEBUG;
    private Set<String> includePackages = Set.of();
    private Set<String> excludePackages = Set.of();

    @Getter
    @RequiredArgsConstructor
    enum LOG_LEVEL {
        TRACE("TRACE"),
        DEBUG("DEBUG"),
        INFO("INFO"),
        WARNING("WARNING"),
        ERROR("ERROR");

        private final String level;

    }
}
