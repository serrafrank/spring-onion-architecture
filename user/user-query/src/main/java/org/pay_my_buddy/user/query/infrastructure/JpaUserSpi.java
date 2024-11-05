package org.pay_my_buddy.user.query.infrastructure;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.common.domain.entity.EntityId;
import org.pay_my_buddy.user.common.domain.UserEntityProjection;
import org.pay_my_buddy.user.query.domain.UserSpi;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class JpaUserSpi implements UserSpi {

    private final UserRepository userRepository;

    @Override
    public Optional<UserEntityProjection> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntityProjection> findUserById(EntityId id) {
        return userRepository.findUserById(id.toString())  ;
    }
}
