package org.pay_my_buddy.modules.user.command.presentation;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.command.CloseUserAccountCommand;
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
public class CloseUserAccountEndpoint {


    private final UserCommandGateway gateway;

    @DeleteMapping("{id}")
    public ResponseEntity<?> closeUserAccount(@Validated @NotBlank @PathVariable String id) {
        UserId userId = new UserId(id);
        final CloseUserAccountCommand command = new CloseUserAccountCommand(userId);
        gateway.handle(command);

        return ResponseEntity.ok().build();
    }
}
