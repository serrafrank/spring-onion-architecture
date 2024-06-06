package org.pay_my_buddy.test_fixtures.faker;

import lombok.Value;
import net.datafaker.Faker;
import org.pay_my_buddy.entity.user.*;
import org.pay_my_buddy.infrastructure.user.BCryptPasswordEncoderTool;

@Value
public class UserFaker {

    private static final Faker faker = new Faker();

    private static final PasswordEncoderTool passwordEncoderTool = new BCryptPasswordEncoderTool();

    RawUser rawUser;

    private UserFaker() {
        this.rawUser = new RawUser(faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(), faker.internet().password(8, 15));
    }

    private UserFaker(String firstName, String lastName, String email, String password) {
        this.rawUser = new RawUser(firstName, lastName, email, password);
    }

    public UserFaker(String firstName, String lastName, Email email, RawPassword password) {
        this.rawUser = new RawUser(firstName, lastName, email.value(), password.value());
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

    public static UserFaker of() {
        return new UserFaker();
    }

    public static EncodedPassword passwordEncoded(RawPassword password) {
        return passwordEncoderTool.encode(password);
    }


}
