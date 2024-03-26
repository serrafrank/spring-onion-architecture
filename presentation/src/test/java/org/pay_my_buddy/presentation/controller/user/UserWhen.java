package org.pay_my_buddy.presentation.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.pay_my_buddy.entity.user.User;
import org.pay_my_buddy.entity.user.spi.UserSpi;
import org.pay_my_buddy.presentation.controllers.Request;
import org.pay_my_buddy.presentation.controllers.user.CreateUserRequest;
import org.pay_my_buddy.presentation.controllers.user.UserCommandController;
import org.pay_my_buddy.presentation.faker.UserFaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@JGivenStage
public class UserWhen extends Stage<UserWhen> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    UserCommandController userCommandController;
    @Autowired
    private UserSpi userSpi;
    private MockMvc mockMvc;

    @ProvidedScenarioState
    private User user;

    @ProvidedScenarioState
    private UserFaker userFaker;


    @ProvidedScenarioState
    private Request request;

    @ProvidedScenarioState
    private ResultActions resultActions;

    @BeforeStage
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userCommandController).build();
    }

    public UserWhen the_user_tries_to_register() throws Exception {
        request = CreateUserRequest.builder()
                .email(userFaker.email().value())
                .password(userFaker.password().value())
                .firstName(userFaker.firstName())
                .lastName(userFaker.lastName())
                .build();

        resultActions = mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));
        return self();
    }
}
