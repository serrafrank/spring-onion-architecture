package org.pay_my_buddy.user_query.infrastructure;

import org.pay_my_buddy.shared.exchange.user.UserEntityProjection;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UserId> {

    Optional<UserEntityProjection> findByEmail(String email);
    Optional<UserEntityProjection> findUserById(UserId id);

}
