package org.pay_my_buddy.user.presentation;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.domain.api.CommandPublisher;
import org.pay_my_buddy.user.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final CommandPublisher commandPublisher;
    private final UserProjectionApi userProjectionApi;


    @GetMapping("{id}")
    ResponseEntity<?> findOne(@NotBlank @PathVariable String id) {
        UserId userId = UserId.of(id);
        return userProjectionApi.findById(userId)
                .map(GetUserResponse::new)
                .map(ResponseEntity.ok()::body)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@Validated @RequestBody CreateUserRequest request) {
        CreateUserCommand command = new CreateUserCommand(request.firstname(), request.lastname(), request.email(), request.password());
        commandPublisher.publish(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(command.id());
    }


    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUser(@Validated @NotBlank @RequestParam String id) {
        UserId userId = UserId.of(id);
        commandPublisher.publish(new DeleteUserCommand(userId));
        return ResponseEntity.ok().build();
    }


}

record CreateUserRequest(
        @NotBlank String firstname,
        @NotBlank String lastname,
        @NotBlank String email,
        @NotBlank String password) {
}

record GetUserResponse(
        String id,
        String firstname,
        String lastname,
        String email) {

    GetUserResponse(User user) {
        this(user.id().toString(), user.firstname(), user.lastname(), user.email());
    }
}