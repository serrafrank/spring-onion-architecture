package org.pay_my_buddy.application.user;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.commun.api.command.CommandHandler;
import org.pay_my_buddy.entity.commun.api.query.QueryBus;
import org.pay_my_buddy.entity.commun.ApplicationService;
import org.pay_my_buddy.entity.user.CreateUserCommand;
import org.pay_my_buddy.entity.user.ExistsUserByEmailQuery;
import org.pay_my_buddy.entity.user.User;
import org.pay_my_buddy.entity.user.UserRepository;

@ApplicationService
@RequiredArgsConstructor
public class CreateUserUseCase implements CommandHandler<CreateUserCommand> {

    private final UserRepository userRepository;
    private final QueryBus queryApi;

    public void handle(CreateUserCommand command) {

        final String firstName = command.firstName();
        final String lastName = command.lastName();
        final String email = command.email();
        final String password = command.password();

        if (userAlreadyExists(email)) {
            throw new IllegalArgumentException("User with email " + email + " already exists");
        }

        final User user = new User(
                firstName,
                lastName,
                email,
                password
        );

        this.userRepository.save(user);

    }

    private Boolean userAlreadyExists(String email) {
        return queryApi.execute(new ExistsUserByEmailQuery(email));
    }

}
