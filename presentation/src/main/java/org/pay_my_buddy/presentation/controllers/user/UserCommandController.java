package org.pay_my_buddy.presentation.controllers.user;

import lombok.AllArgsConstructor;
import org.pay_my_buddy.entity.commun.api.ApiProvider;
import org.pay_my_buddy.entity.user.api.CreateUserCommand;
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
    public ResponseEntity<?> createUser(@Validated @RequestBody CreateUserRequest request) {
        final CreateUserCommand createUserCommand = new CreateUserCommand(request.getEmail(), request.getPassword(), request.getFirstName(), request.getLastName());
        apiProvider.execute(createUserCommand);
        return ResponseEntity.noContent().build();
    }

}
