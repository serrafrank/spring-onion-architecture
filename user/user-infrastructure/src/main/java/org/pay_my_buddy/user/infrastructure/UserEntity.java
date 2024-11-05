package org.pay_my_buddy.user.infrastructure;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.pay_my_buddy.shared.domain.domain_event.UserCreatedEvent;
import org.pay_my_buddy.shared.domain.entity.EntityId;
import org.pay_my_buddy.user.domain.UserEntityProjection;

import java.util.Set;
import java.util.stream.Collectors;

@Table
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public class UserEntity implements UserEntityProjection {

    @Id
    private String id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "friends", joinColumns = @JoinColumn(name = "users_id"))
    @Column(name = "friend", nullable = false)
    private Set<String> friends;

    public UserEntity(UserCreatedEvent event) {
        id = event.id().toString();
        firstname = event.firstname();
        lastname = event.lastname();
        email = event.email();
        password = event.password();
    }


    @Override
    public EntityId id() {
        return EntityId.of(id);
    }


    @Override
    public Set<EntityId> friends() {
        return friends.stream().map(EntityId::of).collect(Collectors.toSet());
    }

    public UserEntity addFriend(EntityId friend) {
        friends.add(String.valueOf(friend));
        return this;
    }


}
