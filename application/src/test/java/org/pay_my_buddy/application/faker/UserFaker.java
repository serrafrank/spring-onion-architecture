package org.pay_my_buddy.application.faker;

import lombok.Value;
import org.pay_my_buddy.entity.user.*;

@Value
public class UserFaker {

    private static final PasswordEncoderTool passwordEncoderTool = new PasswordEncoderToolFaker();

    RawUser rawUser;

    private UserFaker() {
        this.rawUser = new RawUser(FakerTool.data().name().firstName(), FakerTool.data().name().lastName(), FakerTool.data().internet().emailAddress(), FakerTool.password());
    }

    private UserFaker(String firstName, String lastName, String email, String password) {
        this.rawUser = new RawUser(firstName, lastName, email, password);
    }

    public UserFaker(String firstName, String lastName, Email email, RawPassword password) {
        this.rawUser = new RawUser(firstName, lastName, email.value(), password.value());
    }

    public static UserFaker of() {
        return new UserFaker();
    }

    public static EncodedPassword passwordEncoded(RawPassword password) {
        return passwordEncoderTool.encode(password);
    }

    public User build() {
        final EncodedPassword encodedPassword = passwordEncoded(RawPassword.of(this.rawUser.password()));
        return new User(this.rawUser.firstName(), this.rawUser.lastName(), Email.of(this.rawUser.email()), encodedPassword);
    }

    public UserFaker firstName(String firstName) {
        return new UserFaker(firstName, this.rawUser.lastName(), this.rawUser.email(), this.rawUser.password());
    }

    public UserFaker lastName(String lastName) {
        return new UserFaker(this.rawUser.firstName(), lastName, this.rawUser.email(), this.rawUser.password());
    }

    public UserFaker email(Email email) {
        return new UserFaker(this.rawUser.firstName(), this.rawUser.lastName(), email.value(), this.rawUser.password());
    }

    public UserFaker email(String email) {
        return new UserFaker(this.rawUser.firstName(), this.rawUser.lastName(), email, this.rawUser.password());
    }

    public UserFaker password(RawPassword password) {
        return new UserFaker(this.rawUser.firstName(), this.rawUser.lastName(), this.rawUser.email(), password.value());
    }

    public UserFaker password(String password) {
        return new UserFaker(this.rawUser.firstName(), this.rawUser.lastName(), this.rawUser.email(), password);
    }


}
