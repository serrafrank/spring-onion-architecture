package org.pay_my_buddy.core.framework.infrastructure.log_flow;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.core.framework.domain.log_flow.LogFlowExclusion;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.UUID;

import static org.pay_my_buddy.core.framework.domain.log_flow.LogFlowConst.LOG_FLOW_REQUEST_ID;

/**
 * A filter that adds a key to the Mapped Diagnostic Context (MDC) to each request so you can print a unique id in the log messages of each request
 **/

@LogFlowExclusion
@Component
@Slf4j
public class Slf4jMDCFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) {
        try {
            final String requestId = UUID.randomUUID().toString();

            MDC.getMDCAdapter().put(LOG_FLOW_REQUEST_ID, requestId);
            response.addHeader(LOG_FLOW_REQUEST_ID, requestId);
            chain.doFilter(request, response);
        } catch (Exception ex) {
            log.error("Exception occurred in filter while setting UUID for logs", ex);
        } finally {
            MDC.remove(LOG_FLOW_REQUEST_ID);
        }
    }

    @Override
    protected boolean isAsyncDispatch(final HttpServletRequest request) {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}
