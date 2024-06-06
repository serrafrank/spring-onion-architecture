package org.pay_my_buddy.application.use_case.user.command.create_user;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.application.common.DomainService;
import org.pay_my_buddy.application.common.api.ApiProvider;
import org.pay_my_buddy.application.common.api.CommandHandler;
import org.pay_my_buddy.application.common.api.CommandResponse;
import org.pay_my_buddy.application.use_case.user.UserSpi;
import org.pay_my_buddy.application.use_case.user.query.exists_user_by_email.UserExistsByEmailQuery;
import org.pay_my_buddy.entity.user.*;

@DomainService
@RequiredArgsConstructor
public class CreateUserUseCase implements CommandHandler<CreateUserCommand, UserId> {

    private final UserSpi userSpi;
    private final ApiProvider apiProvider;
    private final PasswordEncoderTool passwordEncoder;

    public CommandResponse<UserId> handle(CreateUserCommand command) {

        final Email email = command.email();

        if (userAlreadyExists(email)) {
            throw new EmailAlreadyExistsException(email);
        }

        final String firstName = command.firstName();
        final String lastName = command.lastName();
        final EncodedPassword encodedPassword = passwordEncoder.encode(command.password());

        final User user = User.of(firstName, lastName, email, encodedPassword);

        this.userSpi.save(user);

        return CommandResponse.of(user.id()).addEvent(UserCreatedEvent.of(command, user));
    }

    private boolean userAlreadyExists(Email email) {
        return apiProvider.request(UserExistsByEmailQuery.of(email));
    }

}
