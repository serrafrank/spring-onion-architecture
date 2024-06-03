package org.pay_my_buddy.presentation.controllers.user;

import lombok.AllArgsConstructor;
import org.pay_my_buddy.entity.application.user.api.CreateUserCommand;
import org.pay_my_buddy.entity.common.api.ApiProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserCommandController {

    private final ApiProvider apiProvider;

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@Validated @RequestBody CreateUserRequest request) {
        final CreateUserCommand createUserCommand = CreateUserCommand.of(request.email(), request.password(), request.firstName(), request.lastName());
        apiProvider.execute(createUserCommand);
        return ResponseEntity.noContent().build();
    }

}
