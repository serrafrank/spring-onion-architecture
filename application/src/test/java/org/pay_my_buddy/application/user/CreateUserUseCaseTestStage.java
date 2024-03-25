package org.pay_my_buddy.application.user;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeScenario;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pay_my_buddy.entity.commun.api.query.QueryApi;
import org.pay_my_buddy.entity.commun.value_object.Email;
import org.pay_my_buddy.entity.commun.value_object.EncodedPassword;
import org.pay_my_buddy.entity.commun.value_object.RawPassword;
import org.pay_my_buddy.entity.user.PasswordEncoderTool;
import org.pay_my_buddy.entity.user.User;
import org.pay_my_buddy.entity.user.api.CreateUserCommand;
import org.pay_my_buddy.entity.user.api.ExistsUserByEmailQuery;
import org.pay_my_buddy.entity.user.spi.UserSpi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@JGivenStage
@ExtendWith({MockitoExtension.class})
class CreateUserUseCaseTestStage extends Stage<CreateUserUseCaseTestStage> {


    private final UserSpi userSpi = Mockito.mock(UserSpi.class);


    private final QueryApi queryApi = Mockito.mock(QueryApi.class);


    private final PasswordEncoderTool passwordEncoder = Mockito.mock(PasswordEncoderTool.class);


    private final CreateUserUseCase createUserUseCase = new CreateUserUseCase(userSpi, queryApi, passwordEncoder);


    private final ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

    private CreateUserCommand command;

    private String firstName = "John";
    private String lastName = "Doe";
    private String email = "john.doe@test.com";
    private String password = "Password123!";

    private final String passwordSuffix = "_encoded";
    private Exception exception;


    @BeforeScenario
    void setUp() {
        lenient().when(passwordEncoder.encode(any(RawPassword.class))).thenAnswer(i -> encodePassword(i.getArguments()[0]));
    }

    public CreateUserUseCaseTestStage a_new_user() {
        createCommand();
        return self();
    }

    public CreateUserUseCaseTestStage firstName(@Quoted String firstName) {
        this.firstName = firstName;
        createCommand();
        return self();
    }

    public CreateUserUseCaseTestStage lastName(@Quoted String lastName) {
        this.lastName = lastName;
        createCommand();
        return self();
    }

    public CreateUserUseCaseTestStage email(@Quoted String email) {
        this.email = email;
        createCommand();
        return self();
    }

    public CreateUserUseCaseTestStage password(@Quoted String password) {
        this.password = password;
        createCommand();
        return self();
    }


    public CreateUserUseCaseTestStage the_email_is_not_used() {
        final ExistsUserByEmailQuery existsUserByEmailQuery = new ExistsUserByEmailQuery(Email.of(email));
        Mockito.when(queryApi.request(existsUserByEmailQuery)).thenReturn(false);
        return self();
    }


    public CreateUserUseCaseTestStage the_email_is_already_used() {
        final ExistsUserByEmailQuery existsUserByEmailQuery = new ExistsUserByEmailQuery(Email.of(email));
        Mockito.when(queryApi.request(existsUserByEmailQuery)).thenReturn(true);
        return self();
    }

    public CreateUserUseCaseTestStage the_user_try_to_create_an_account() {
        try {
            createUserUseCase.handle(command);
        } catch (Exception e) {
            this.exception = e;

        }
        return self();
    }

    public CreateUserUseCaseTestStage the_user_is_created() {
        verify(userSpi).save(userArgumentCaptor.capture());
        final User user = userArgumentCaptor.getValue();
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail().value());
        assertEquals(encodePassword(password), user.getPassword());
        return self();
    }

    public CreateUserUseCaseTestStage an_email_already_exists_exception_is_thrown() {
        assertNotNull(exception);
        assertEquals(EmailAlreadyExistsException.class, exception.getClass());
        return self();
    }

    public CreateUserUseCaseTestStage the_user_creation_fails() {
        verify(userSpi, never()).save(any());
        return self();
    }


    private void createCommand() {
        this.command = new CreateUserCommand(Email.of(email), RawPassword.of(password), firstName, lastName);
    }

    private EncodedPassword encodePassword(Object password) {
        switch (password) {
            case RawPassword rawPassword -> {
                return encodePassword(rawPassword.value());
            }
            case String string -> {
                return encodePassword(string);
            }
            default -> throw new IllegalArgumentException("Unsupported password type: " + password.getClass());
        }
    }

}