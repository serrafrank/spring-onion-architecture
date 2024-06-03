package org.pay_my_buddy.presentation.controller.user;

import com.tngtech.jgiven.integration.spring.junit5.SimpleSpringScenarioTest;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.presentation.PresentationContext;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = {MockServletContext.class, PresentationContext.class})
@WebAppConfiguration
@AutoConfigureMockMvc
class CreateUserTest extends SimpleSpringScenarioTest<CreateUserStage> {

    @Test
    void a_user_can_be_created() throws Exception {
        given().a_user();
        when().the_user_tries_to_register();
        then().the_user_is_registered();
    }
}
