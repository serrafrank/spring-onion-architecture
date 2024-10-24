package org.pay_my_buddy.user.infrastructure.dal;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.user.domain.User;
import org.pay_my_buddy.user.domain.UserId;
import org.pay_my_buddy.user.domain.UserProjectionApi;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaUserProjectionApi implements UserProjectionApi {

    private final UserRepository repository;

    @Override
    public Optional<User> findById(UserId id) {
        return repository.findById(id.toString())
                .map(UserEntity::mapToDomain);
    }
}
