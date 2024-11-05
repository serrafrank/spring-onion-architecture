package org.pay_my_buddy.user.infrastructure;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.domain.entity.EntityId;
import org.pay_my_buddy.user.domain.UserEntityProjection;
import org.pay_my_buddy.user.domain.UserSpi;
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
