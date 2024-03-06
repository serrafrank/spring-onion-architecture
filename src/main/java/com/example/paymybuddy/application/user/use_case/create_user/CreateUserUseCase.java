package com.example.paymybuddy.application.user.use_case.create_user;

import com.example.paymybuddy.application.shared.message_handler.CommandHandler;
import com.example.paymybuddy.application.user.domain.UserAggregate;
import com.example.paymybuddy.application.user.spi.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserUseCase implements CommandHandler<CreateUserCommand> {

    private final UserRepository userRepository;
//    private final QueryBus queryBus;

    public void execute(CreateUserCommand command) {

        final String firstName = command.firstName();
        final String lastName = command.lastName();
        final String email = command.email();
        final String password = command.password();

//        if (queryBus.ask(new ExistsUserByEmailQuery(email))) {
//            throw new IllegalArgumentException("User with email " + email + " already exists");
//        }

        final UserAggregate user = new UserAggregate(
                firstName,
                lastName,
                email,
                password
        );

        this.userRepository.save(user);

    }

}
