package org.pay_my_buddy.entity.user;

import com.tngtech.jgiven.junit5.SimpleScenarioTest;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.entity.commun.entity.GenericId;
import org.pay_my_buddy.entity.commun.entity.Id;

class UserTest extends SimpleScenarioTest<UserTestStages> {

    public Id newFriendId = GenericId.of();

    @Test
    void a_user_update_his_first_name() {
        given().a_user();
        when().the_user_update_his_first_name_to_$("Jane");
        then().the_user_is_correctly_updated()
                .and().no_exception_is_thrown();
    }

    @Test
    void a_user_update_his_last_name() {
        given().a_user();
        when().the_user_update_his_last_name_to_$("Doe");
        then().the_user_is_correctly_updated()
                .and().no_exception_is_thrown();
    }

    @Test
    void a_user_update_his_email() {
        given().a_user();
        when().the_user_update_his_email_to_$("other.email@test.com");
        then().the_user_is_correctly_updated()
                .and().no_exception_is_thrown();
    }

    @Test
    void a_user_update_his_password() {
        given().a_user();
        when().the_user_update_his_password_to_$("newPassword");
        then().the_user_is_correctly_updated()
                .and().no_exception_is_thrown();
    }

    @Test
    void a_user_add_a_friend() {
        given().a_user()
                .and().the_user_has_$_friends(1);
        when().the_user_add_a_friend_with_id_$(newFriendId);
        then().the_user_is_correctly_updated()
                .and().the_user_has_$_friends(2);
    }

    @Test
    void a_user_remove_a_friend() {
        given().a_user()
                .and().the_user_add_a_friend_with_id_$(newFriendId)
                .and().the_user_has_$_friends(2);
        when().the_user_remove_a_friend_with_id_$(newFriendId);
        then().the_user_is_correctly_updated()
                .and().the_user_has_$_friends(1);
    }

    @Test
    void a_user_add_himself_as_a_friend() {
        given().a_user();
        when().the_user_add_himself_as_a_friend();
        then().an_exception_$_is_thrown(IllegalArgumentException.class);
    }

}