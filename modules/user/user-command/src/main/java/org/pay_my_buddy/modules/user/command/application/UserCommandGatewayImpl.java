package org.pay_my_buddy.modules.user.command.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.core.framework.domain.DomainService;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.command.*;

@DomainService
@RequiredArgsConstructor
@Slf4j
public class UserCommandGatewayImpl implements UserCommandGateway {

    private final AddFriendUseCase addFriendUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final CloseUserAccountUseCase closeUserAccountUseCase;
    private final RemoveFriendUseCase removeFriendUseCase;

    @Override
    public void handle(AddFriendCommand command) {
        addFriendUseCase.handle(command);
    }

    @Override
    public UserId handle(CreateUserCommand command) {
        return createUserUseCase.handle(command);
    }

    @Override
    public void handle(CloseUserAccountCommand command) {
        closeUserAccountUseCase.handle(command);
    }

    @Override
    public void handle(RemoveFriendCommand command) {
        removeFriendUseCase.handle(command);
    }
}
