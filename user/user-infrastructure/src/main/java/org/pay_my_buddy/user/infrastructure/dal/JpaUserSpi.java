package org.pay_my_buddy.user.infrastructure.dal;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.domain.entity.Id;
import org.pay_my_buddy.user.domain.User;
import org.pay_my_buddy.user.domain.UserSpi;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class JpaUserSpi implements UserSpi {

    private final UserRepository userRepository;

    @Override
    public boolean emailAlreadyExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void delete(User user) {
        userRepository.deleteById(String.valueOf(user.id()));
    }

    @Override
    public Optional<User> findById(Id id) {
        return userRepository.findById(id.toString())
                .map(UserEntity::mapToDomain);
    }

    @Override
    public void persist(User domainEntity) {
        userRepository.save(new UserEntity(domainEntity));
    }
}
