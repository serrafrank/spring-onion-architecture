package org.pay_my_buddy.user_query.infrastructure;

import jakarta.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.pay_my_buddy.shared.exchange.user.UserEntityProjection;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.pay_my_buddy.shared.exchange.user.command.UserCreatedEvent;

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
    @Column(name = "friendId", nullable = false)
    private Set<String> friends;

    public UserEntity(UserCreatedEvent event) {
        id = event.id().value();
        firstname = event.firstname();
        lastname = event.lastname();
        email = event.email();
        password = event.password();
    }


    @Override
    public UserId userId() {
        return new UserId(id);
    }


    @Override
    public Set<UserId> friends() {
        return friends.stream().map(UserId::new).collect(Collectors.toSet());
    }

    public UserEntity addFriend(UserId friend) {
        friends.add(friend.value());
        return this;
    }

    public UserEntity removeFriend(UserId friend) {
        friends.remove(friend.value());
        return this;
    }
}
