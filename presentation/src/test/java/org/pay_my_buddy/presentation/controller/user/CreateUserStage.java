package org.pay_my_buddy.presentation.controller.user;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeScenario;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.pay_my_buddy.entity.application.user.spi.UserSpi;
import org.pay_my_buddy.entity.user.User;
import org.pay_my_buddy.presentation.controllers.Request;
import org.pay_my_buddy.presentation.controllers.user.CreateUserRequest;
import org.pay_my_buddy.presentation.controllers.user.UserCommandController;
import org.pay_my_buddy.presentation.faker.UserFaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@JGivenStage
public class CreateUserStage extends Stage<CreateUserStage> {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    private UserFaker userFaker;

    private User user;

    private Request request;

    private ResultActions resultActions;


    @BeforeScenario
    public void setUp() {
        userFaker = new UserFaker();
    }

    public CreateUserStage a_user() {
        user = userFaker.create();
        return self();
    }

    public CreateUserStage the_user_tries_to_register() throws Exception {
        request = new CreateUserRequest(
                user.email().value(),
                user.password().value(),
                user.firstName(),
                user.lastName());

        resultActions = mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));
        return self();
    }

    public CreateUserStage the_user_is_registered() throws Exception {
        resultActions.andExpect(status().isCreated());
        return self();
    }


}
