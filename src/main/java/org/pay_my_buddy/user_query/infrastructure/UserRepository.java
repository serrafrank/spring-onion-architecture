package org.pay_my_buddy.user_query.infrastructure;

import java.util.Optional;
import org.pay_my_buddy.shared.exchange.user.UserEntityProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntityProjection> findByEmail(String email);

    Optional<UserEntityProjection> findUserById(String id);

}
