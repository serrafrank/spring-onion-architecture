package org.pay_my_buddy.presentation.api.providers;

import org.pay_my_buddy.application.common.api.QueryHandler;
import org.springframework.context.ApplicationContext;

/**
 * QueryProvider creates a handler with enabled spring injection.
 *
 * @param <H> type of handler
 */
public record QueryProvider<H extends QueryHandler<?, ?>>(ApplicationContext applicationContext, Class<H> type) {

  public H get() {
    return applicationContext.getBean(type);
  }
}
