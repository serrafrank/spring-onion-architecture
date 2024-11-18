package org.pay_my_buddy.user_query.presentation;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.api_query.QueryBus;
import org.pay_my_buddy.shared.exchange.user.UserEntityProjection;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.pay_my_buddy.shared.exchange.user.query.FindUserByIdQuery;
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
        UserId userId = new UserId(id);

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
            String email,
            List<String> friends) {

        GetUserResponse(UserEntityProjection projection) {
            this(projection.userId().value(),
                    projection.firstname(),
                    projection.lastname(),
                    projection.email(),
                    projection.friends().stream()
                            .map(UserId::value)
                            .toList()
            );
        }
    }
}
