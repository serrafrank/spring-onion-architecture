package org.pay_my_buddy.user.domain;

import org.pay_my_buddy.shared.domain.exception.BadArgumentException;

public class CreateUserUseCase implements CreateUserApi {

    private final UserSpi userSpi;

    public CreateUserUseCase(UserSpi userSpi) {
        this.userSpi = userSpi;
    }

    @Override
    public void handle(CreateUserCommand command) {
        if (userSpi.emailAlreadyExists(command.email())) {
            throw new BadArgumentException("Email already exists");
        }

        User user = User.create(command.id(), command.firstname(), command.lastname(), command.email(), command.password());

        userSpi.persist(user);
    }
}
