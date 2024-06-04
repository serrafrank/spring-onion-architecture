package org.pay_my_buddy.presentation.controller.user;

import com.tngtech.jgiven.integration.spring.junit5.SimpleSpringScenarioTest;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.presentation.PresentationContext;
import org.pay_my_buddy.presentation.faker.UserFaker;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;

@SpringBootTest(classes = {MockServletContext.class, PresentationContext.class})
@AutoConfigureMockMvc
class CreateUserTest extends SimpleSpringScenarioTest<CreateUserStage> {


    @Test
    void a_user_can_be_created() throws Exception {
        given().a_user(UserFaker.with());
        when().the_user_tries_to_register();
        then().the_user_is_registered();
    }
}
