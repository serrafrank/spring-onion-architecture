package org.pay_my_buddy.presentation.controllers.user;

import lombok.AllArgsConstructor;
import org.pay_my_buddy.application.common.api.ApiProvider;
import org.pay_my_buddy.application.use_case.user.command.create_user.CreateUserCommand;
import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.user.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserCommandController {

    private final ApiProvider apiProvider;

    @PostMapping
    public ResponseEntity<UserId> createUser(@Validated @RequestBody CreateUserRequest request) {
        UserId userId = UserId.of();
        final CreateUserCommand createUserCommand = CreateUserCommand.of(userId, request.email(), request.password(), request.firstName(), request.lastName());
        apiProvider.execute(createUserCommand);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

}
