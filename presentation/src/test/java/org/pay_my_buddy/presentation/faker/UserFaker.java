package org.pay_my_buddy.presentation.faker;

import lombok.Value;
import net.datafaker.Faker;
import org.pay_my_buddy.entity.user.*;
import org.pay_my_buddy.infrastructure.user.BCryptPasswordEncoderTool;

@Value
public class UserFaker {

    private static final Faker faker = new Faker();

    private static final PasswordEncoderTool passwordEncoderTool = new BCryptPasswordEncoderTool();

    private final String firstName;
    private final String lastName;
    private final Email email;
    private final RawPassword password;

    private UserFaker() {
        this.firstName = faker.name().firstName();
        this.lastName = faker.name().lastName();
        this.email = Email.of(faker.internet().emailAddress());
        this.password = RawPassword.of(faker.internet().password(8, 15));
    }

    private UserFaker(String firstName, String lastName, String  email,String  password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = Email.of(email);
        this.password = RawPassword.of(password);
    }

    public User build() {
        return new User(firstName, lastName, email, passwordEncoded(password));
    }

    public UserFaker firstName(String firstName) {
        return new UserFaker(firstName, lastName, email.value(), password.value());
    }

    public UserFaker lastName(String lastName) {
        return new UserFaker(firstName, lastName, email.value(), password.value());
    }

    public UserFaker email(Email email) {
        return new UserFaker(firstName, lastName, email.value(), password.value());
    }

    public UserFaker password(RawPassword password) {
        return new UserFaker(firstName, lastName, email.value(), password.value());
    }


    public static User create() {
        return new UserFaker().build();
    }

    public static UserFaker with() {
        return new UserFaker();
    }

    public static EncodedPassword passwordEncoded(RawPassword password) {
        return passwordEncoderTool.encode(password);
    }


}
