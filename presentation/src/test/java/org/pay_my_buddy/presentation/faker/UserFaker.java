package org.pay_my_buddy.presentation.faker;

import lombok.Data;
import lombok.experimental.Accessors;
import net.datafaker.Faker;
import org.pay_my_buddy.entity.user.*;
import org.pay_my_buddy.infrastructure.user.BCryptPasswordEncoderTool;

@Data
@Accessors(chain = true, fluent = true)
public class UserFaker {

    private static final Faker faker = new Faker();

    private static final PasswordEncoderTool passwordEncoderTool = new BCryptPasswordEncoderTool();

    private String firstName = faker.name().firstName();
    private String lastName = faker.name().lastName();
    private Email email = Email.of(faker.internet().emailAddress());
    private RawPassword password = RawPassword.of(faker.internet().password(8, 20, true, true, true));

    public User create() {
        return new User(firstName, lastName, email, passwordEncoded());
    }

    public EncodedPassword passwordEncoded() {
        return passwordEncoderTool.encode(password);
    }

    public UserFaker email(String email) {
        this.email = Email.of(email);
        return this;
    }

    public UserFaker password(String password) {
        this.password = RawPassword.of(password);
        return this;
    }


}
