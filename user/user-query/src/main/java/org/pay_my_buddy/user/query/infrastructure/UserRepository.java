package org.pay_my_buddy.user.query.infrastructure;

import org.pay_my_buddy.user.common.domain.UserEntityProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntityProjection> findByEmail(String email);
    Optional<UserEntityProjection> findUserById(String id);

}