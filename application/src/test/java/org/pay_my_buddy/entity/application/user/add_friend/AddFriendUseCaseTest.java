package org.pay_my_buddy.entity.application.user.add_friend;

import com.tngtech.jgiven.integration.spring.EnableJGiven;
import com.tngtech.jgiven.integration.spring.junit5.SimpleSpringScenarioTest;
import com.tngtech.jgiven.junit5.JGivenExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@EnableJGiven
@ExtendWith(JGivenExtension.class)
class AddFriendUseCaseTest extends SimpleSpringScenarioTest<AddFriendStage> {

    @Test
    void friend_is_added_successfully() {
        given().a_valid_user()
                .and().a_valid_friend()
                .and().an_AddFriendCommand_is_created()
                .and().the_friend_is_not_already_a_friend();
        when().the_AddFriendCommand_is_handled();
        then().the_friend_is_added_to_the_user_friend_list_and_the_user_is_saved_successfully();
    }

    @Test
    void friend_does_not_exist() {
        given().a_valid_user()
                .and().a_valid_friend()
                .and().an_AddFriendCommand_is_created()
                .and().the_friend_does_not_exist();
        when().the_AddFriendCommand_is_handled();
        then().the_handler_throws_a_FriendNotFoundException();
    }

    @Test
    void user_does_not_exist() {
        given().a_valid_user()
                .and().a_valid_friend()
                .and().an_AddFriendCommand_is_created()
                .and().the_user_does_not_exist();
        when().the_AddFriendCommand_is_handled();
        then().the_handler_throws_an_UserNotFoundException();
    }

    @Test
    void friend_is_already_a_friend() {
        given().a_valid_user()
                .and().a_valid_friend()
                .and().an_AddFriendCommand_is_created()
                .and().the_friend_is_already_a_friend();
        when().the_AddFriendCommand_is_handled();
        then().the_handler_throws_an_UserIsAlreadyAFriendException();
    }

    @Test
    void user_tries_to_add_themselves_as_a_friend() {
        given().a_user_select_himself();
        when().an_AddFriendCommand_is_created();
        then().the_command_throws_an_IllegalArgumentException();
    }
}