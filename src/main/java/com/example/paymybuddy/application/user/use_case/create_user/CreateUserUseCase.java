package com.example.paymybuddy.application.user.use_case.create_user;

import com.example.paymybuddy.application.EventBus;
import com.example.paymybuddy.application.CommandUseCase;
import com.example.paymybuddy.application.QueryBus;
import com.example.paymybuddy.application.user.spi.UserRepository;
import com.example.paymybuddy.application.user.query.ExistsUserByEmailQuery;
import com.example.paymybuddy.application.user.domain.UserAggregate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserUseCase implements CommandUseCase<CreateUserCommand> {

    private final UserRepository userRepository;
    private final QueryBus queryBus;
    private final EventBus eventBus;

    public void execute(CreateUserCommand command){

        final String firstName = command.firstName();
        final String lastName = command.lastName();
        final String email = command.email();
        final String password = command.password();

        if (queryBus.ask(new ExistsUserByEmailQuery(email))) {
            throw new IllegalArgumentException("User with email " + email + " already exists");
        }

        final UserAggregate user = new UserAggregate(
                firstName,
                lastName,
                email,
                password
        );

        this.userRepository.save(user);

        final UserCreatedEvent event = new UserCreatedEvent(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
        eventBus.publish(event);
    }

}
