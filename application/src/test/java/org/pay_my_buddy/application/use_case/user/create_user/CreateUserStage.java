package org.pay_my_buddy.application.use_case.user.create_user;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.pay_my_buddy.application.common.api.ApiProvider;
import org.pay_my_buddy.application.faker.PasswordEncoderToolFaker;
import org.pay_my_buddy.application.use_case.user.UserSpi;
import org.pay_my_buddy.application.use_case.user.command.create_user.CreateUserCommand;
import org.pay_my_buddy.application.use_case.user.command.create_user.CreateUserUseCase;
import org.pay_my_buddy.application.use_case.user.command.create_user.EmailAlreadyExistsException;
import org.pay_my_buddy.application.use_case.user.query.exists_user_by_email.UserExistsByEmailQuery;
import org.pay_my_buddy.entity.user.PasswordEncoderTool;
import org.pay_my_buddy.entity.user.RawPassword;
import org.pay_my_buddy.entity.user.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@JGivenStage
class CreateUserStage extends Stage<CreateUserStage> {


    private final UserSpi userSpi = Mockito.mock(UserSpi.class);


    private final ApiProvider apiProvider = Mockito.mock(ApiProvider.class);


    private final PasswordEncoderTool passwordEncoder = new PasswordEncoderToolFaker();


    private final CreateUserUseCase createUserUseCase = new CreateUserUseCase(userSpi, apiProvider, passwordEncoder);


    private final ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
    private CreateUserCommand command;
    private String firstName = "John";
    private String lastName = "Doe";
    private String email = "john.doe@test.com";
    private String password = "Password123!";
    private Exception exception;

    public CreateUserStage a_new_user() {
        createCommand();
        return self();
    }

    public CreateUserStage firstName(@Quoted String firstName) {
        this.firstName = firstName;
        createCommand();
        return self();
    }

    public CreateUserStage lastName(@Quoted String lastName) {
        this.lastName = lastName;
        createCommand();
        return self();
    }

    public CreateUserStage email(@Quoted String email) {
        this.email = email;
        createCommand();
        return self();
    }

    public CreateUserStage password(@Quoted String password) {
        this.password = password;
        createCommand();
        return self();
    }


    public CreateUserStage the_email_is_not_used() {
        Mockito.when(apiProvider.request(any(UserExistsByEmailQuery.class))).thenReturn(false);
        return self();
    }


    public CreateUserStage the_email_is_already_used() {
        Mockito.when(apiProvider.request(any(UserExistsByEmailQuery.class))).thenReturn(true);
        return self();
    }

    public CreateUserStage the_user_try_to_create_an_account() {
        try {
            createUserUseCase.handle(command);
        } catch (Exception e) {
            this.exception = e;

        }
        return self();
    }

    public CreateUserStage the_user_is_created() {
        verify(userSpi).save(userArgumentCaptor.capture());
        final User user = userArgumentCaptor.getValue();
        assertEquals(firstName, user.firstName());
        assertEquals(lastName, user.lastName());
        assertEquals(email, user.email().value());
        assertTrue(passwordEncoder.matches(RawPassword.of(password), user.password()));
        return self();
    }

    public CreateUserStage an_email_already_exists_exception_is_thrown() {
        assertNotNull(exception);
        assertEquals(EmailAlreadyExistsException.class, exception.getClass());
        return self();
    }

    public CreateUserStage the_user_creation_fails() {
        verify(userSpi, never()).save(any());
        return self();
    }


    private void createCommand() {
        this.command = CreateUserCommand.of(email, password, firstName, lastName);
    }



}