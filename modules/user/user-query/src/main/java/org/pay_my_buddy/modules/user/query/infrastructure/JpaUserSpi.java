package org.pay_my_buddy.modules.user.query.infrastructure;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.modules.user.query.application.UserSpi;
import org.pay_my_buddy.modules.user.shared.UserEntityProjection;
import org.pay_my_buddy.modules.user.shared.UserId;
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
    public Optional<UserEntityProjection> findUserById(UserId id) {
        return userRepository.findUserById(id.value());
    }
}
