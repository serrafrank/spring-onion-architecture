package org.pay_my_buddy.presentation.controller.user;


import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.pay_my_buddy.presentation.controllers.user.CreateUserRequest;
import org.pay_my_buddy.presentation.faker.UserFaker;
import org.pay_my_buddy.presentation.helper.ResultActionsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@JGivenStage
public class CreateUserStage extends Stage<CreateUserStage> {

    @Autowired
    private MockMvc mockMvc;


    private UserFaker user;

    private CreateUserRequest request;

    private ResultActions resultActions;

    public CreateUserStage a_user(UserFaker user) {
        this.user = user;
        return self();
    }

    public CreateUserStage the_user_tries_to_register() throws Exception {
        request = new CreateUserRequest()
                .email(user.email().value())
                .password(user.password().value())
                .firstName(user.firstName())
                .lastName(user.lastName());

        resultActions = mockMvc.perform(post("/users")
                .content(ResultActionsHelper.toJson(request))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        return self();
    }

    public CreateUserStage the_user_is_registered() throws Exception {
        resultActions.andExpect(status().isCreated());
        return self();
    }


}
