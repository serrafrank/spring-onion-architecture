package org.pay_my_buddy.application.user;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.commun.ApplicationService;
import org.pay_my_buddy.entity.commun.api.command.CommandHandler;
import org.pay_my_buddy.entity.commun.api.query.QueryApi;
import org.pay_my_buddy.entity.user.PasswordEncoderTool;
import org.pay_my_buddy.entity.user.User;
import org.pay_my_buddy.entity.user.api.CreateUserCommand;
import org.pay_my_buddy.entity.user.api.ExistsUserByEmailQuery;
import org.pay_my_buddy.entity.user.spi.UserSpi;

@ApplicationService
@RequiredArgsConstructor
public class CreateUserUseCase implements CommandHandler<CreateUserCommand> {

    private final UserSpi userSpi;
    private final QueryApi queryApi;
    private final PasswordEncoderTool passwordEncoder;

    public void handle(CreateUserCommand command) {

        final String firstName = command.firstName();
        final String lastName = command.lastName();
        final String email = command.email();
        final String password = passwordEncoder.encode(command.password());

        if (userAlreadyExists(email)) {
            throw new EmailAlreadyExistsException(email);
        }

        final User user = new User(
                firstName,
                lastName,
                email,
                password
        );

        this.userSpi.save(user);

    }

    private Boolean userAlreadyExists(String email) {
        return queryApi.request(new ExistsUserByEmailQuery(email));
    }

}
