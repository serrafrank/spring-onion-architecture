package org.pay_my_buddy.presentation.controller.user;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.pay_my_buddy.presentation.controllers.Request;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@JGivenStage
public class UserThen extends Stage<UserThen> {


    @ProvidedScenarioState
    private Request request;

    @ProvidedScenarioState
    private ResultActions resultActions;

    public UserThen the_user_is_registered() throws Exception {
        resultActions.andExpect(status().isCreated());
        return self();
    }
}
