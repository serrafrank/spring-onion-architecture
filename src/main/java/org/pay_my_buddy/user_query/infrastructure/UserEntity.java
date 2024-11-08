package org.pay_my_buddy.user_query.infrastructure;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.pay_my_buddy.shared.exchange.user.command.UserCreatedEvent;
import org.pay_my_buddy.shared.exchange.user.UserEntityProjection;
import org.pay_my_buddy.shared.exchange.user.UserId;

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
    private UserId id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "friends", joinColumns = @JoinColumn(name = "users_id"))
    @Column(name = "friend", nullable = false)
    private Set<String> friends;

    public UserEntity(UserCreatedEvent event) {
        id = event.id();
        firstname = event.firstname();
        lastname = event.lastname();
        email = event.email();
        password = event.password();
    }


    @Override
    public UserId id() {
        return UserId.of(id);
    }


    @Override
    public Set<UserId> friends() {
        return friends.stream().map(UserId::of).collect(Collectors.toSet());
    }

    public UserEntity addFriend(UserId friend) {
        friends.add(String.valueOf(friend));
        return this;
    }


}
