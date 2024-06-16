package org.pay_my_buddy.presentation.api.providers;

import org.pay_my_buddy.application.common.api.EventHandler;
import org.springframework.context.ApplicationContext;

/**
 * EventProvider creates a handler with enabled spring injection.
 *
 * @param <H> type of handler
 */
public record EventProvider<H extends EventHandler<?>>(ApplicationContext applicationContext, Class<H> type) {

  public H get() {
    return applicationContext.getBean(type);
  }
}
