package org.pay_my_buddy.application.use_case.user.command.create_user;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.application.common.DomainService;
import org.pay_my_buddy.application.common.api.CommandHandler;
import org.pay_my_buddy.application.common.api.CommandResponse;
import org.pay_my_buddy.application.common.api.QueryApi;
import org.pay_my_buddy.application.use_case.user.UserSpi;
import org.pay_my_buddy.application.use_case.user.query.exists_user_by_email.UserExistsByEmailQuery;
import org.pay_my_buddy.entity.user.*;

@DomainService
@RequiredArgsConstructor
public class CreateUserUseCase implements CommandHandler<CreateUserCommand, UserId> {

    private final UserSpi userSpi;
    private final QueryApi queryApi;
    private final PasswordEncoderTool passwordEncoder;

    public CommandResponse<UserId> handle(CreateUserCommand command) {

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

        return CommandResponse.of(user.id());
    }

    private boolean userAlreadyExists(Email email) {
        return queryApi.request(UserExistsByEmailQuery.of(email));
    }

}
