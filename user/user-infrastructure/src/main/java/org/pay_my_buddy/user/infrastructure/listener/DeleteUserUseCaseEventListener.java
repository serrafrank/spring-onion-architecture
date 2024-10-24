package org.pay_my_buddy.user.infrastructure.listener;

import jakarta.transaction.Transactional;
import org.pay_my_buddy.user.domain.DeleteUserCommand;
import org.pay_my_buddy.user.domain.DeleteUserUseCase;
import org.pay_my_buddy.user.domain.UserSpi;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserUseCaseEventListener extends DeleteUserUseCase {

    public DeleteUserUseCaseEventListener(UserSpi userSpi) {
        super(userSpi);
    }

    @Transactional
    @EventListener
    public void commandHandler(DeleteUserCommand command) {
        this.handle(command);
    }
}
