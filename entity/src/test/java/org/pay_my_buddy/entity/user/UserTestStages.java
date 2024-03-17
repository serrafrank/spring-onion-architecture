package org.pay_my_buddy.entity.user;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.pay_my_buddy.entity.commun.entity.GenericId;
import org.pay_my_buddy.entity.commun.entity.Id;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@JGivenStage
class UserTestStages extends Stage<UserTestStages> {

    private Id friendId = GenericId.of();

    private UserId userId = UserId.of();
    private String firstName = "John";
    private String lastName = "Doe";
    private String email = "john.doe@test.com";
    private String password = "password";
    private User user;

    private Exception exception;

    public UserTestStages a_user() {
        this.user = new User(userId, firstName, lastName, email, password, Set.of(friendId));
        return self();
    }

    public UserTestStages the_user_update_his_first_name_to_$(@Quoted String firstName) {
        this.firstName = firstName;
        a_user();
        return self();
    }

    public UserTestStages the_user_update_his_last_name_to_$(@Quoted String lastName) {
        this.lastName = lastName;
        a_user();
        return self();
    }

    public UserTestStages the_user_update_his_email_to_$(@Quoted String email) {
        this.email = email;
        a_user();
        return self();
    }

    public UserTestStages the_user_update_his_password_to_$(@Quoted String password) {
        this.password = password;
        a_user();
        return self();
    }

    public UserTestStages the_user_add_a_friend_with_id_$(Id id) {
        try {
            this.user.addFriend(id);
        } catch (Exception e) {
            this.exception = e;
        }
        return self();
    }

    public UserTestStages the_user_remove_a_friend_with_id_$(Id id) {
        try {
            this.user.removeFriend(id);
        } catch (Exception e) {
            this.exception = e;
        }
        return self();
    }

    public UserTestStages the_user_add_himself_as_a_friend() {
        try {
            this.user.addFriend(this.user.getId());
        } catch (Exception e) {
            this.exception = e;
        }
        return self();
    }

    public UserTestStages the_user_is_correctly_updated() {
        assertThat(this.user.getId()).isEqualTo(this.userId);
        assertThat(this.user.getFirstName()).isEqualTo(this.firstName);
        assertThat(this.user.getLastName()).isEqualTo(this.lastName);
        assertThat(this.user.getEmail()).isEqualTo(this.email);
        assertThat(this.user.getPassword()).isEqualTo(this.password);
        return self();
    }

    public UserTestStages the_user_has_$_friends(int numberOfFriends) {
        assertThat(this.user.getFriends()).hasSize(numberOfFriends);
        return self();
    }

    public UserTestStages an_exception_$_is_thrown(Class<? extends Exception> exception) {
        assertThat(this.exception).isNotNull();
        assertThat(this.exception).isInstanceOf(exception);
        return self();
    }

    public UserTestStages no_exception_is_thrown() {
        assertThat(this.exception).isNull();
        return self();
    }
}