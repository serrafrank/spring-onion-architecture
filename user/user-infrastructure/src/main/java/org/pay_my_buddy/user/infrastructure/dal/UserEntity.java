package org.pay_my_buddy.user.infrastructure.dal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;
import org.pay_my_buddy.user.domain.User;
import org.pay_my_buddy.user.domain.UserId;
import org.springframework.data.domain.AbstractAggregateRoot;

@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends AbstractAggregateRoot<UserEntity> {

    @Id
    private String id;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String email;

    @Column
    private String password;

    public UserEntity(User user) {
        this.id = user.id().toString();
        this.firstname = user.firstname();
        this.lastname = user.lastname();
        this.email = user.email();
        this.password = user.password();
        user.domainEvents().forEach(this::registerEvent);
    }

    public User mapToDomain() {
        return User.of(UserId.of(id), firstname, lastname, email, password);

    }
}
