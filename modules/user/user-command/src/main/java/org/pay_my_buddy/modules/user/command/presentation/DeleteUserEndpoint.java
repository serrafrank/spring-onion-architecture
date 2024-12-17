package org.pay_my_buddy.modules.user.command.presentation;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.command.DeleteUserCommand;
import org.pay_my_buddy.modules.user.shared.command.UserCommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class DeleteUserEndpoint {


    private final UserCommandGateway gateway;

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@Validated @NotBlank @PathVariable String id) {
        UserId userId = new UserId(id);
        final DeleteUserCommand command = new DeleteUserCommand(userId);
        try {
            gateway.handle(command);
        } catch (Exception ignore) {
            log.warn("Failed to delete user with id {}", id);
        }

        return ResponseEntity.ok().build();
    }
}
