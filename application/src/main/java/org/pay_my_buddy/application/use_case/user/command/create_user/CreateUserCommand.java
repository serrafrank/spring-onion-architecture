package org.pay_my_buddy.application.use_case.user.command.create_user;


import lombok.EqualsAndHashCode;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import org.pay_my_buddy.application.common.api.AbstractCommand;
import org.pay_my_buddy.entity.user.Email;
import org.pay_my_buddy.entity.user.RawPassword;
import org.pay_my_buddy.entity.user.UserId;

@Value
@EqualsAndHashCode(callSuper = true)
public class CreateUserCommand extends AbstractCommand<UserId> {
    Email email;
    RawPassword password;
    String firstName;
    String lastName;


    private CreateUserCommand(Email email, RawPassword password, String firstName, String lastName) {
        if (StringUtils.isBlank(firstName)) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }

        if (StringUtils.isBlank(lastName)) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static CreateUserCommand of(String email, String password, String firstName, String lastName) {
        return new CreateUserCommand(Email.of(email), new RawPassword(password), firstName, lastName);
    }

}
