package org.pay_my_buddy.user_command.presentation;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.api_command.CommandBus;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.pay_my_buddy.shared.exchange.user.command.DeleteUserCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class DeleteUserEndpoint {


    private final CommandBus publisher;


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@Validated @NotBlank @PathVariable String id) {
        UserId userId = new UserId(id);
        try {
            publisher.execute(new DeleteUserCommand(userId));
        } catch (Exception ignore) {
        }

        return ResponseEntity.ok().build();
    }
}
