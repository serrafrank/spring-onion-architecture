package org.pay_my_buddy.bootloader.controller.user;


import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.pay_my_buddy.application.use_case.user.UserSpi;
import org.pay_my_buddy.bootloader.controller.ResultActionsHelper;
import org.pay_my_buddy.entity.user.User;
import org.pay_my_buddy.entity.user.UserId;
import org.pay_my_buddy.presentation.controllers.user.CreateUserRequest;
import org.pay_my_buddy.presentation.controllers.user.CreateUserResponse;
import org.pay_my_buddy.test_fixtures.faker.RawUser;
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


    private RawUser user;

    private ResultActions resultActions;

    public CreateUserStage a_user(RawUser user) {
        this.user = user;
        return self();
    }

    public CreateUserStage the_user_tries_to_register() throws Exception {
        CreateUserRequest request = new CreateUserRequest()
                .email(user.email())
                .password(user.password())
                .firstName(user.firstName())
                .lastName(user.lastName());

        resultActions = mockMvc.perform(post("/api/v1/users")
                .content(ResultActionsHelper.toJson(request))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        return self();
    }

    public CreateUserStage the_controller_return_a_created_status() throws Exception {
        resultActions.andExpect(status().isCreated());
        return self();
    }

    public CreateUserStage the_user_is_registered() throws Exception {
        CreateUserResponse response = ResultActionsHelper.toObject(resultActions, CreateUserResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.userId()).isNotNull();

        Optional<User> userInMemory = userSpi.findUser(UserId.of(response.userId()));
        assertThat(userInMemory)
                .isNotNull()
                .isPresent()
                .get()
                .satisfies(u -> {
                    assertThat(u.email().value()).isEqualTo(user.email());
                    assertThat(u.firstName()).isEqualTo(user.firstName());
                    assertThat(u.lastName()).isEqualTo(user.lastName());
                });
        return self();
    }


}
