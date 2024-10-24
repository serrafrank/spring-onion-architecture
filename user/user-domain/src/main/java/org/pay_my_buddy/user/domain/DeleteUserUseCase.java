package org.pay_my_buddy.user.domain;

import java.util.Optional;

public class DeleteUserUseCase implements DeleteUserApi {

    private final UserSpi userSpi;

    public DeleteUserUseCase(UserSpi userSpi) {
        this.userSpi = userSpi;
    }

    @Override
    public void handle(DeleteUserCommand command) {
        Optional<User> user = userSpi.findById(command.id());

        user.ifPresent(userSpi::delete);
    }
}
