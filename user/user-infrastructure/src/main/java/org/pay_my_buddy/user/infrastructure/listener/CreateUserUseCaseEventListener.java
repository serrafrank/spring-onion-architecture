package org.pay_my_buddy.user.infrastructure.listener;

import jakarta.transaction.Transactional;
import org.pay_my_buddy.user.domain.CreateUserCommand;
import org.pay_my_buddy.user.domain.CreateUserUseCase;
import org.pay_my_buddy.user.domain.UserSpi;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CreateUserUseCaseEventListener extends CreateUserUseCase {

    public CreateUserUseCaseEventListener(UserSpi userSpi) {
        super(userSpi);
    }

    @Transactional
    @EventListener
    public void commandHandler(CreateUserCommand command) {
        this.handle(command);
    }
}
