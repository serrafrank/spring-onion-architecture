package org.pay_my_buddy.modules.user.command.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.core.framework.domain.DomainService;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.command.AddFriendCommand;
import org.pay_my_buddy.modules.user.shared.command.CreateUserCommand;
import org.pay_my_buddy.modules.user.shared.command.DeleteUserCommand;
import org.pay_my_buddy.modules.user.shared.command.UserCommandGateway;

@DomainService
@RequiredArgsConstructor
@Slf4j
public class UserCommandGatewayImpl implements UserCommandGateway {

    private final AddFriendUseCase addFriendUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @Override
    public void handle(AddFriendCommand command) {
        addFriendUseCase.handle(command);
    }

    @Override
    public UserId handle(CreateUserCommand command) {
        return createUserUseCase.handle(command);
    }

    @Override
    public void handle(DeleteUserCommand command) {
        deleteUserUseCase.handle(command);
    }
}
