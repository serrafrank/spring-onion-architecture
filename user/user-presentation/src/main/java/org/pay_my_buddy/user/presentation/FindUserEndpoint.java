package org.pay_my_buddy.user.presentation;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.domain.api.query.QueryBus;
import org.pay_my_buddy.user.domain.UserEntityProjection;
import org.pay_my_buddy.user.domain.UserId;
import org.pay_my_buddy.user.domain.find_user_by_id.FindUserByIdQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class FindUserEndpoint {

    private final QueryBus queryBus;


    @GetMapping("{id}")
    ResponseEntity<?> findOne(@NotBlank @PathVariable String id) {
        UserId userId = UserId.of(id);

        var query = new FindUserByIdQuery(userId);
        return queryBus.ask(query)
                .map(GetUserResponse::new)
                .map(ResponseEntity.ok()::body)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public record GetUserResponse(
            String id,
            String firstname,
            String lastname,
            String email) {

        GetUserResponse(UserEntityProjection projection) {
            this(projection.id().toString(), projection.firstname(), projection.lastname(), projection.email());
        }
    }
}
