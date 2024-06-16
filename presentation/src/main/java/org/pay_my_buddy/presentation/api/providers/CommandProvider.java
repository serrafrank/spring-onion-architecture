package org.pay_my_buddy.presentation.api.providers;

import lombok.Value;
import org.pay_my_buddy.application.common.api.CommandHandler;
import org.springframework.context.ApplicationContext;

/**
 * CommandProvider creates a handler with enabled spring injection.
 *
 * @param <H> type of handler
 */
public record CommandProvider<H extends CommandHandler<?, ?>>(ApplicationContext applicationContext, Class<H> type) {

  public H get() {
    return applicationContext.getBean(type);
  }
}
