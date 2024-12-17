package org.pay_my_buddy.modules.user.command.presentation;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.command.CreateUserCommand;
import org.pay_my_buddy.modules.user.shared.command.UserCommandGateway;
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

    private final UserCommandGateway gateway;

    @PostMapping()
    public ResponseEntity<?> createUser(@Validated @RequestBody CreateUserRequest request) {
        CreateUserCommand command = new CreateUserCommand(request.firstname(), request.lastname(), request.email(), request.password());
        final UserId userId = gateway.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateUserResponse(userId));
    }

    public record CreateUserRequest(
            @NotBlank String firstname,
            @NotBlank String lastname,
            @NotBlank String email,
            @NotBlank String password) {
    }

    public record CreateUserResponse(
            String id) {

        public CreateUserResponse(UserId id) {
            this(id.value());
        }

    }


}
