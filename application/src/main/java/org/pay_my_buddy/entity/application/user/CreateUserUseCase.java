package org.pay_my_buddy.entity.application.user;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.common.DomainService;
import org.pay_my_buddy.entity.application.user.api.CreateUserCommand;
import org.pay_my_buddy.entity.application.user.api.ExistsUserByEmailQuery;
import org.pay_my_buddy.entity.application.user.spi.UserSpi;
import org.pay_my_buddy.entity.common.api.command.CommandHandler;
import org.pay_my_buddy.entity.common.api.command.EventList;
import org.pay_my_buddy.entity.common.api.query.QueryApi;
import org.pay_my_buddy.entity.user.Email;
import org.pay_my_buddy.entity.user.EncodedPassword;
import org.pay_my_buddy.entity.user.PasswordEncoderTool;
import org.pay_my_buddy.entity.user.User;

@DomainService
@RequiredArgsConstructor
public class CreateUserUseCase implements CommandHandler<CreateUserCommand> {

    private final UserSpi userSpi;
    private final QueryApi queryApi;
    private final PasswordEncoderTool passwordEncoder;

    public EventList handle(CreateUserCommand command) {

        final String firstName = command.firstName();
        final String lastName = command.lastName();
        final Email email = command.email();
        final EncodedPassword password = passwordEncoder.encode(command.password());

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

        return EventList.empty();
    }

    private boolean userAlreadyExists(Email email) {
        return queryApi.request(ExistsUserByEmailQuery.of(email));
    }

}
