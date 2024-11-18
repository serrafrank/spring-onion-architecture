package org.pay_my_buddy.user_query.infrastructure;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.exchange.user.UserEntityProjection;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.pay_my_buddy.user_query.application.UserSpi;
import org.springframework.stereotype.Component;


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
