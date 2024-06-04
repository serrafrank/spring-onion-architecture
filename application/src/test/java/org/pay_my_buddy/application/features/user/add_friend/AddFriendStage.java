package org.pay_my_buddy.application.features.user.add_friend;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.mockito.Mockito;
import org.pay_my_buddy.application.faker.UserFaker;
import org.pay_my_buddy.application.features.user.AddFriendUseCase;
import org.pay_my_buddy.application.features.user.FriendNotFoundException;
import org.pay_my_buddy.application.features.user.UserIsAlreadyAFriendException;
import org.pay_my_buddy.application.features.user.api.AddFriendCommand;
import org.pay_my_buddy.application.features.user.spi.UserSpi;
import org.pay_my_buddy.entity.exception.UserNotFoundException;
import org.pay_my_buddy.entity.user.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@JGivenStage
public class AddFriendStage extends Stage<AddFriendStage> {


    UserSpi userSpi = Mockito.mock(UserSpi.class);
    User friend;
    User user;

    AddFriendCommand command;

    Exception handlerException;

    Exception commandException;

    public AddFriendStage a_valid_user() {
        user = UserFaker.create();
        Mockito.when(userSpi.existsById(user.id())).thenReturn(true);
        Mockito.when(userSpi.findUser(user.id())).thenReturn(Optional.of(user));
        return self();
    }

    public AddFriendStage a_valid_friend() {
        friend = UserFaker.create();
        Mockito.when(userSpi.existsById(friend.id())).thenReturn(true);
        Mockito.when(userSpi.findUser(friend.id())).thenReturn(Optional.of(friend));
        return self();
    }

    public AddFriendStage an_AddFriendCommand_is_created() {
        try {
            command = AddFriendCommand.of(user.id(), friend.id());
        } catch (Exception e) {
            commandException = e;
        }

        return self();
    }

    public AddFriendStage the_friend_is_not_already_a_friend() {
        return self();
    }

    public AddFriendStage the_friend_is_already_a_friend() {
        user.addFriend(friend.id());
        return self();
    }

    public AddFriendStage the_user_does_not_exist() {
        Mockito.when(userSpi.findUser(command.userId())).thenReturn(Optional.empty());
        return self();
    }

    public AddFriendStage the_friend_does_not_exist() {
        Mockito.when(userSpi.existsById(command.userId())).thenReturn(true);
        Mockito.when(userSpi.existsById(command.friendId())).thenReturn(false);
        return self();
    }

    public AddFriendStage a_user_select_himself() {
        user = UserFaker.create();
        friend = user;
        return self();
    }

    public AddFriendStage the_AddFriendCommand_is_handled() {
        if (handlerException != null) {
            return self();
        }

        try {
            new AddFriendUseCase(userSpi).handle(command);
        } catch (Exception e) {
            handlerException = e;
        }
        return self();
    }

    public AddFriendStage the_friend_is_added_to_the_user_friend_list_and_the_user_is_saved_successfully() {
        assertThat(handlerException).isNull();
        assertThat(commandException).isNull();
        verify(userSpi, times(1)).save(any());
        return self();
    }

    public AddFriendStage the_handler_throws_an_UserNotFoundException() {
        assertThat(handlerException).isNotNull();
        assertThat(commandException).isNull();
        assertThat(handlerException).isInstanceOf(UserNotFoundException.class);
        verify(userSpi, never()).save(any());
        return self();
    }

    public AddFriendStage the_handler_throws_a_FriendNotFoundException() {
        assertThat(handlerException).isNotNull();
        assertThat(commandException).isNull();
        assertThat(handlerException).isInstanceOf(FriendNotFoundException.class);
        verify(userSpi, never()).save(any());
        return self();
    }

    public AddFriendStage the_handler_throws_an_UserIsAlreadyAFriendException() {
        assertThat(commandException).isNull();
        assertThat(handlerException).isNotNull()
                .isInstanceOf(UserIsAlreadyAFriendException.class);
        verify(userSpi, never()).save(any());
        return self();
    }

    public AddFriendStage the_command_throws_an_IllegalArgumentException() {
        assertThat(handlerException).isNull();
        assertThat(commandException).isNotNull()
                .isInstanceOf(IllegalArgumentException.class);
        verify(userSpi, never()).save(any());
        return self();
    }

    public void the_handler_exception_message_is(String message) {
        assertThat(handlerException).isNotNull();
        assertThat(handlerException.getMessage()).isEqualTo(message);
    }

    public void the_command_exception_message_is(String message) {
        assertThat(commandException).isNotNull();
        assertThat(commandException.getMessage()).isEqualTo(message);
    }
}