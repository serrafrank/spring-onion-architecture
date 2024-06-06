package org.pay_my_buddy.application.use_case.user.command.authenticate_user;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.pay_my_buddy.application.common.api.AbstractCommand;
import org.pay_my_buddy.entity.user.Email;
import org.pay_my_buddy.entity.user.RawPassword;

@Value
@EqualsAndHashCode(callSuper = true)
public class AuthenticateUserCommand extends AbstractCommand<AuthenticationResponse> {
    Email email;
    RawPassword password;

    private AuthenticateUserCommand(Email email, RawPassword password) {
        this.email = email;
        this.password = password;
    }

    public static AuthenticateUserCommand of(Email email, RawPassword password) {
        return new AuthenticateUserCommand(email, password);
    }


}
