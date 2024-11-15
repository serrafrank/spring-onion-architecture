package org.pay_my_buddy.user_command.presentation;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.api_command.CommandBus;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.pay_my_buddy.shared.exchange.user.command.CreateUserCommand;
import org.pay_my_buddy.user_command.application.CreateUserPresenter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class CreateUserEndpoint {

    private final CommandBus publisher;
    private final CreateUserPresenter presenter;

    @PostMapping()
    public ResponseEntity<?> createUser(@Validated @RequestBody CreateUserRequest request) {
        CreateUserCommand command = new CreateUserCommand(request.firstname(), request.lastname(), request.email(), request.password());
        publisher.execute(command);
        final UserId id = presenter.view();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreateUserResponse(id));
    }

    public record CreateUserRequest(
            @NotBlank String firstname,
            @NotBlank String lastname,
            @NotBlank String email,
            @NotBlank String password) {
    }

    public record CreateUserResponse(
            String id) {

        CreateUserResponse(UserId id) {
            this(id.value());
        }
    }

}
