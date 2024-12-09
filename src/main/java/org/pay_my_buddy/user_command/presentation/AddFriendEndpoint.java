package org.pay_my_buddy.user_command.presentation;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.api_command.CommandBus;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.pay_my_buddy.shared.exchange.user.command.AddFriendCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AddFriendEndpoint {


    private final CommandBus publisher;

    @PostMapping("/{userId}/add-friend")
    public ResponseEntity<?> AddFriend(
            @Validated @NotBlank @PathVariable String userId,
            @Validated @RequestBody AddFriendRequest request) {
        AddFriendCommand command = new AddFriendCommand(new UserId(userId), new UserId(request.friendId()));
        publisher.execute(command);
        return ResponseEntity.ok().build();
    }

    public record AddFriendRequest(@NotBlank String friendId) {
    }

}
