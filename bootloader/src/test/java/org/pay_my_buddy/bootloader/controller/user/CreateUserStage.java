package org.pay_my_buddy.bootloader.controller.user;


import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.pay_my_buddy.application.use_case.user.UserSpi;
import org.pay_my_buddy.bootloader.controller.faker.UserFaker;
import org.pay_my_buddy.bootloader.controller.helper.ResultActionsHelper;
import org.pay_my_buddy.entity.user.User;
import org.pay_my_buddy.entity.user.UserId;
import org.pay_my_buddy.presentation.controllers.user.CreateUserRequest;
import org.pay_my_buddy.presentation.controllers.user.CreateUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@JGivenStage
public class CreateUserStage extends Stage<CreateUserStage> {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserSpi userSpi;


    private UserFaker user;

    private ResultActions resultActions;

    public CreateUserStage a_user(UserFaker user) {
        this.user = user;
        return self();
    }

    public CreateUserStage the_user_tries_to_register() throws Exception {
        CreateUserRequest request = new CreateUserRequest()
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
        CreateUserResponse response = ResultActionsHelper.toObject(resultActions, CreateUserResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.userId()).isNotNull();

        Optional<User> userInMemory = userSpi.findUser(UserId.of(response.userId()));
        assertThat(userInMemory)
                .isNotNull()
                .isPresent()
                .get()
                .satisfies(u -> {
                    assertThat(u.email().value()).isEqualTo(user.email().value());
                    assertThat(u.firstName()).isEqualTo(user.firstName());
                    assertThat(u.lastName()).isEqualTo(user.lastName());
                });
        return self();
    }


}
