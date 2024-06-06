package org.pay_my_buddy.bootloader.controller.user;

import com.tngtech.jgiven.integration.spring.junit5.SimpleSpringScenarioTest;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.bootloader.controller.faker.UserFaker;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CreateUserTest extends SimpleSpringScenarioTest<CreateUserStage> {


    @Test
    void a_user_can_be_created() throws Exception {
        given().a_user(UserFaker.with());
        when().the_user_tries_to_register();
        then().the_controller_return_a_created_status()
                .and().the_user_is_registered();
    }
}
