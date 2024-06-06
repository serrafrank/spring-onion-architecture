package org.pay_my_buddy.application.use_case.user.command.authenticate_user;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.application.common.DomainService;
import org.pay_my_buddy.application.common.api.CommandHandler;
import org.pay_my_buddy.application.common.api.CommandResponse;
import org.pay_my_buddy.application.use_case.user.UserSpi;
import org.pay_my_buddy.entity.user.EncodedPassword;
import org.pay_my_buddy.entity.user.PasswordEncoderTool;
import org.pay_my_buddy.entity.user.RawPassword;
import org.pay_my_buddy.entity.user.UserNotFoundException;

/**
 * This class represents the use case of adding a friend to a user's friend list.
 * It implements the CommandHandler interface with AddFriendCommand as the command type.
 */
@DomainService
@RequiredArgsConstructor
public class AuthenticateUserUseCase implements CommandHandler<AuthenticateUserCommand, AuthenticationResponse> {

    // UserSpi is used to interact with the User entity
    private final UserSpi userSpi;
    private final PasswordEncoderTool passwordEncoderTool;
    private final tokenService tokenService;

    @Override
    public CommandResponse<AuthenticationResponse> handle(AuthenticateUserCommand command) {
        var user = userSpi.findUser(command.email())
                .orElseThrow(() -> new UserNotFoundException(command.email()));

        if (passwordNotValid(command.password(), user.password())) {
            throw new UserNotFoundException(command.email());
        }

        String accessToken = tokenService.generateAccessToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);

        var response = AuthenticationResponse.of(accessToken, refreshToken);

        return CommandResponse.of(response).add(UserAuthenticatedEvent.of(command, user));

    }

    private boolean passwordNotValid(RawPassword rawPassword, EncodedPassword encodedPassword) {
        return !passwordEncoderTool.matches(rawPassword, encodedPassword);
    }
}