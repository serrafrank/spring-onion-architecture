package org.pay_my_buddy.user.command.presentation;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.common.domain.api.command.CommandBus;
import org.pay_my_buddy.shared.common.domain.entity.EntityId;
import org.pay_my_buddy.user.common.domain.AddFriendCommand;
import org.pay_my_buddy.user.common.domain.UserId;
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
            @Validated @PathVariable String userId,
            @Validated @RequestBody AddFriendRequest request) {
        AddFriendCommand command = new AddFriendCommand(EntityId.of(userId), UserId.of(request.friendId()));
        publisher.execute(command);
        return ResponseEntity.ok().build();
    }

    public record AddFriendRequest(@NotBlank String friendId) {
    }

}
