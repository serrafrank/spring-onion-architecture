package org.pay_my_buddy.presentation.controller.user;


import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeScenario;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.pay_my_buddy.entity.user.User;
import org.pay_my_buddy.presentation.controllers.Request;
import org.pay_my_buddy.presentation.controllers.user.CreateUserRequest;
import org.pay_my_buddy.presentation.faker.UserFaker;

@JGivenStage
public class UserGiven extends Stage<UserGiven> {

    @ProvidedScenarioState
    private User user;

    @ProvidedScenarioState
    private UserFaker userFaker;

    @ProvidedScenarioState
    private Request request;

    @BeforeScenario
    public void setUp() {
        userFaker = new UserFaker();
    }

    public UserGiven a_user() {
        user = userFaker.create();
        return self();
    }

    public UserGiven first_name_is_$(@Quoted String firstName) {
        user = userFaker.firstName(firstName).create();
        return self();
    }

    public UserGiven last_name_is_$(@Quoted String lastName) {
        user = userFaker.lastName(lastName).create();
        return self();
    }

    public UserGiven email_is_$(@Quoted String email) {
        user = userFaker.email(email).create();
        return self();
    }

    public UserGiven password_is_$(@Quoted String password) {
        user = userFaker.password(password).create();
        return self();
    }

    public UserGiven the_user_exists() {
        user = userFaker.create();
        return self();
    }


    public UserGiven the_user_tries_to_register() {
        request = CreateUserRequest.builder()
                .email(user.email().value())
                .password(user.password().value())
                .firstName(user.firstName())
                .lastName(user.lastName())
                .build();
        return self();
    }

}
