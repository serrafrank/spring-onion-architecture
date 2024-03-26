package org.pay_my_buddy.presentation.controller.user;

import com.tngtech.jgiven.integration.spring.junit5.SpringScenarioTest;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.presentation.PresentationContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = {MockServletContext.class, PresentationContext.class})
@WebAppConfiguration
class CreateUserTest extends SpringScenarioTest<UserGiven, UserWhen, UserThen> {

    @Test
    void a_user_can_be_created() throws Exception {
        given().a_user();
        when().the_user_tries_to_register();
        then().the_user_is_registered();
    }
}
